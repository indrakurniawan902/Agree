package com.agree.ecosystem.core.utils.data.reqres.web.token

import com.agree.ecosystem.core.utils.data.reqres.model.token.RefreshTokenBodyPost
import com.agree.ecosystem.core.utils.data.reqres.model.token.RefreshTokenItem
import com.devbase.data.source.web.WebService
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response

class AgreeTokenApi(
    private val api: AgreeTokenApiClient
) : AgreeTokenApiClient, WebService {
    override fun singleRefreshToken(data: RefreshTokenBodyPost): Single<Response<DevApiResponse<RefreshTokenItem>>> {
        return api.singleRefreshToken(data)
    }

    override fun observableRefreshToken(data: RefreshTokenBodyPost): Observable<Response<DevApiResponse<RefreshTokenItem>>> {
        return api.observableRefreshToken(data)
    }

    override fun flowableRefreshToken(data: RefreshTokenBodyPost): Flowable<Response<DevApiResponse<RefreshTokenItem>>> {
        return api.flowableRefreshToken(data)
    }
}
