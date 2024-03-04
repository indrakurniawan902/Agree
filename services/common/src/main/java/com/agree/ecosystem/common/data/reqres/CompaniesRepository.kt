package com.agree.ecosystem.common.data.reqres

import com.agree.ecosystem.common.data.reqres.model.home.CompanyPartnerItem
import com.devbase.data.source.DevRepository
import io.reactivex.Flowable

interface CompaniesRepository : DevRepository {
    fun getCompanyPartner(): Flowable<List<CompanyPartnerItem>>
}
