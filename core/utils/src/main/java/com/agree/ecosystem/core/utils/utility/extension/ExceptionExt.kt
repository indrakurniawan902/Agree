package com.agree.ecosystem.core.utils.utility.extension

import com.agree.ecosystem.core.utils.domain.reqres.model.ErrorResponse
import com.agree.ecosystem.core.utils.utility.exception.NetworkException
import com.devbase.data.utilities.exception.ApiException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.networkError(): Pair<Throwable, Int> {
    return runCatching {
        when (this) {
            is HttpException -> {
                val type = object : TypeToken<ErrorResponse>() {}.type
                val errorResponse: ErrorResponse? =
                    Gson().fromJson(this.response()?.errorBody()?.charStream(), type)
                val error = Throwable(cause = this, message = errorResponse?.message.toString())
                Pair(error, errorResponse?.code ?: 404)
            }
            is ApiException -> {
                val error = Throwable(cause = this, message = this.apiError.message)
                Pair(error, this.apiError.code)
            }
            is SocketTimeoutException, is UnknownHostException -> {
                val error = NetworkException(this.message.orEmpty())
                Pair(error, 999)
            }
            else -> Pair(this, 404)
        }
    }.getOrDefault(Pair(this, 404))
}
