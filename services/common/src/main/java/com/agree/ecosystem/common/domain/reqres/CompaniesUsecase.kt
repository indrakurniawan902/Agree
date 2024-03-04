package com.agree.ecosystem.common.domain.reqres

import com.agree.ecosystem.common.domain.reqres.model.home.companypartner.CompanyPartner
import io.reactivex.Flowable

interface CompaniesUsecase {
    fun getCompanyPartner(): Flowable<List<CompanyPartner>>
}
