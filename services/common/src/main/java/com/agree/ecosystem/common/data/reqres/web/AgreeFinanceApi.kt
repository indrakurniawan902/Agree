package com.agree.ecosystem.common.data.reqres.web

import com.agree.ecosystem.common.data.reqres.model.home.MyLoanActiveItem
import com.devbase.data.source.web.WebService
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import retrofit2.Response

class AgreeFinanceApi(
    private val api: AgreeFinanceApiClient
) : AgreeFinanceApiClient, WebService {
    override fun fetchListLoanActive(userId: String?): Flowable<Response<DevApiResponse<List<MyLoanActiveItem>>>> {
        return api.fetchListLoanActive(userId)
    }
}
