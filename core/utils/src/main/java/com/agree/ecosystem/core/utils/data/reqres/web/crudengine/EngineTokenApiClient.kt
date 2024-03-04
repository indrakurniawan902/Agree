package com.agree.ecosystem.core.utils.data.reqres.web.crudengine

import com.agree.ecosystem.core.utils.data.reqres.model.token.RefreshTokenEngineBodyPost
import com.agree.ecosystem.core.utils.data.reqres.model.token.RefreshTokenEngineItem
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface EngineTokenApiClient {
    @POST("engine/v1/login")
    fun singleRefreshTokenEngine(
        @Body data: RefreshTokenEngineBodyPost
    ): Single<Response<DevApiResponse<RefreshTokenEngineItem>>>

    @POST("engine/v1/login")
    fun flowableRefreshTokenEngine(
        @Body data: RefreshTokenEngineBodyPost
    ): Flowable<Response<DevApiResponse<RefreshTokenEngineItem>>>
}
