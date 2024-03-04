package com.agree.ecosystem.common.data.reqres.web

import com.agree.ecosystem.common.data.reqres.model.home.CompanyPartnerItem
import com.devbase.data.source.web.WebService
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import retrofit2.Response

class AgreeCompaniesApi(
    private val api: AgreeCompaniesApiClient
) : AgreeCompaniesApiClient, WebService {

    override fun getCompanyPartner(page: Int, quantity: Int, sortBy: String): Flowable<Response<DevApiResponse<List<CompanyPartnerItem>>>> {
        return api.getCompanyPartner()
    }
}
