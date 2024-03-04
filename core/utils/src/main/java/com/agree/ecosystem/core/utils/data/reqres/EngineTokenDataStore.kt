package com.agree.ecosystem.core.utils.data.reqres

import com.agree.ecosystem.core.utils.data.reqres.model.token.RefreshTokenEngineBodyPost
import com.agree.ecosystem.core.utils.data.reqres.model.token.RefreshTokenEngineItem
import com.agree.ecosystem.core.utils.data.reqres.web.crudengine.EngineTokenApi
import com.devbase.data.source.db.DbService
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Single
import retrofit2.Response

class EngineTokenDataStore(
    override val dbService: DbService?,
    override val webService: EngineTokenApi
) : EngineTokenRepository {
    override fun singleRefreshToken(
        username: String,
        password: String,
        duration: Int
    ): Single<Response<DevApiResponse<RefreshTokenEngineItem>>> {
        return webService.singleRefreshTokenEngine(
            RefreshTokenEngineBodyPost(
                username,
                password,
                duration
            )
        )
    }
}
