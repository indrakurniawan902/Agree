package com.agree.ecosystem.common.data.reqres.web

import com.agree.ecosystem.common.data.reqres.model.home.MyLoanActiveItem
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AgreeFinanceApiClient {
    @GET("api/v1/modal/loans-history-mobile/{userId}")
    fun fetchListLoanActive(
        @Path("userId") userId: String?
    ): Flowable<Response<DevApiResponse<List<MyLoanActiveItem>>>>
}
