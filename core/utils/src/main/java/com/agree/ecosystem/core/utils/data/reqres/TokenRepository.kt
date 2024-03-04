package com.agree.ecosystem.core.utils.data.reqres

import com.agree.ecosystem.core.utils.data.reqres.model.token.RefreshTokenItem
import com.devbase.data.source.DevRepository
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response

interface TokenRepository : DevRepository {
    fun singleRefreshToken(oldToken: String): Single<Response<DevApiResponse<RefreshTokenItem>>>
    fun observableRefreshToken(oldToken: String): Observable<Response<DevApiResponse<RefreshTokenItem>>>
    fun flowableeRefreshToken(oldToken: String): Flowable<Response<DevApiResponse<RefreshTokenItem>>>
}
