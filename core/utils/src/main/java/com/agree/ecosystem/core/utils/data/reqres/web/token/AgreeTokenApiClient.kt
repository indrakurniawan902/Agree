package com.agree.ecosystem.core.utils.data.reqres.web.token

import com.agree.ecosystem.core.utils.data.reqres.model.token.RefreshTokenBodyPost
import com.agree.ecosystem.core.utils.data.reqres.model.token.RefreshTokenItem
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AgreeTokenApiClient {
    @POST("users/v1/users/refresh-token")
    fun singleRefreshToken(
        @Body data: RefreshTokenBodyPost
    ): Single<Response<DevApiResponse<RefreshTokenItem>>>

    @POST("users/v1/users/refresh-token")
    fun observableRefreshToken(
        @Body data: RefreshTokenBodyPost
    ): Observable<Response<DevApiResponse<RefreshTokenItem>>>

    @POST("users/v1/users/refresh-token")
    fun flowableRefreshToken(
        @Body data: RefreshTokenBodyPost
    ): Flowable<Response<DevApiResponse<RefreshTokenItem>>>
}
