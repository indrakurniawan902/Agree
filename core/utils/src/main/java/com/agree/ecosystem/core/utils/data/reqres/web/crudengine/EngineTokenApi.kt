package com.agree.ecosystem.core.utils.data.reqres.web.crudengine

import com.agree.ecosystem.core.utils.data.reqres.model.token.RefreshTokenEngineBodyPost
import com.agree.ecosystem.core.utils.data.reqres.model.token.RefreshTokenEngineItem
import com.devbase.data.source.web.WebService
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response

class EngineTokenApi(
    private val api: EngineTokenApiClient
) : EngineTokenApiClient, WebService {
    override fun singleRefreshTokenEngine(data: RefreshTokenEngineBodyPost): Single<Response<DevApiResponse<RefreshTokenEngineItem>>> {
        return api.singleRefreshTokenEngine(data)
    }

    override fun flowableRefreshTokenEngine(data: RefreshTokenEngineBodyPost): Flowable<Response<DevApiResponse<RefreshTokenEngineItem>>> {
        TODO("Not yet implemented")
    }
}
