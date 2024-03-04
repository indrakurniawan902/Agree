package com.agree.ecosystem.core.utils.data.reqres

import com.agree.ecosystem.core.utils.data.reqres.model.token.RefreshTokenEngineItem
import com.devbase.data.source.DevRepository
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Single
import retrofit2.Response

interface EngineTokenRepository : DevRepository {
    fun singleRefreshToken(username: String, password: String, duration: Int): Single<Response<DevApiResponse<RefreshTokenEngineItem>>>
}
