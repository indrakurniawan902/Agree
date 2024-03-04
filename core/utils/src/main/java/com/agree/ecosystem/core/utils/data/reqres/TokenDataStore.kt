package com.agree.ecosystem.core.utils.data.reqres

import com.agree.ecosystem.core.utils.data.reqres.model.token.RefreshTokenBodyPost
import com.agree.ecosystem.core.utils.data.reqres.model.token.RefreshTokenItem
import com.agree.ecosystem.core.utils.data.reqres.web.token.AgreeTokenApi
import com.devbase.data.source.db.DbService
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response

class TokenDataStore(
    override val dbService: DbService?,
    override val webService: AgreeTokenApi
) : TokenRepository {
    override fun singleRefreshToken(oldToken: String): Single<Response<DevApiResponse<RefreshTokenItem>>> {
        return webService.singleRefreshToken(RefreshTokenBodyPost(oldToken))
    }

    override fun observableRefreshToken(oldToken: String): Observable<Response<DevApiResponse<RefreshTokenItem>>> {
        return webService.observableRefreshToken(RefreshTokenBodyPost(oldToken))
    }

    override fun flowableeRefreshToken(oldToken: String): Flowable<Response<DevApiResponse<RefreshTokenItem>>> {
        return webService.flowableRefreshToken(RefreshTokenBodyPost(oldToken))
    }
}
