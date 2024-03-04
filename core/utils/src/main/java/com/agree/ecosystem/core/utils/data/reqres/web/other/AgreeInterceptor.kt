package com.agree.ecosystem.core.utils.data.reqres.web.other

import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.AppConfig
import com.agree.ecosystem.core.utils.utility.CredentialManagement
import com.agree.ecosystem.core.utils.utility.extension.containsAll
import com.google.firebase.crashlytics.FirebaseCrashlytics
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.HttpException

class AgreeInterceptor(
    private val prefs: AgrPrefUsecase
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = when {
            chain.request().url.encodedPath.whiteListBasicTokenValidation() -> {
                chain.request().newBuilder()
                    .addHeader("Authorization", "Basic ${CredentialManagement.getBasicToken()}")
                    .build()
            }
            chain.request().url.encodedPath.contains("engine") -> {
                chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${prefs.accessTokenEngine}")
                    .build()
            }
            chain.request().url.encodedPath.contains("agreepedia") -> {
                chain.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        "Basic ${CredentialManagement.getAgreepediaToken()}"
                    )
                    .build()
            }
            else -> {
                chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${prefs.accessToken}")
                    .build()
            }
        }

        val response: Response = chain.proceed(request)

        if (response.code in 400..500 && !AppConfig.isDebug) {
            FirebaseCrashlytics.getInstance().log("Url : " + request.url.toString())
            FirebaseCrashlytics.getInstance()
                .log("Response : " + response.peekBody(Long.MAX_VALUE).string())
            FirebaseCrashlytics.getInstance().recordException(
                HttpException(
                    retrofit2.Response.error<Any>(
                        response.code,
                        response.peekBody(Long.MAX_VALUE)
                    )
                )
            )
        }
        return response
    }

    private fun String.whiteListBasicTokenValidation(): Boolean {
        return prefs.accessToken.isBlank() or
            validationUtilitiesEndPoint() or
            containsAll(
                "login",
                "refresh-token",
                "otp",
                "verify-otp-code",
                "register"
            )
    }

    private fun String.validationUtilitiesEndPoint(): Boolean {
        return this.contains("utilities") && !this.contains("inbox")
    }
}
