package com.agree.ecosystem.common.data.reqres.web

import com.agree.ecosystem.common.data.reqres.model.home.CompanyPartnerItem
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AgreeCompaniesApiClient {
    @GET("partners/v1/companies")
    fun getCompanyPartner(
        @Query("page") page: Int = 1,
        @Query("quantity") quantity: Int = 5,
        @Query("sort_by") sortBy: String = "company_member"
    ): Flowable<Response<DevApiResponse<List<CompanyPartnerItem>>>>
}
