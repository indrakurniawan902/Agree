package com.agree.ecosystem.common.domain.reqres

import com.agree.ecosystem.common.data.reqres.CompaniesRepository
import com.agree.ecosystem.common.domain.reqres.model.home.companypartner.CompanyPartner
import io.reactivex.Flowable

class CompaniesInteractor(
    private val repo: CompaniesRepository
) : CompaniesUsecase {

    override fun getCompanyPartner(): Flowable<List<CompanyPartner>> {
        return repo.getCompanyPartner().map {
            it.map { item ->
                item.toCompanyPartner()
            }
        }
    }
}
