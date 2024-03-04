package com.agree.ecosystem.partnership.presentation.menu.companies

import com.agree.ecosystem.partnership.domain.reqres.model.company.Company

interface CompaniesDataContract {

    fun fetchListCompany()

    fun fetchLoadMoreListCompany()

    fun onGetCompaniesLoading()

    fun onGetCompaniesSuccess(data: List<Company>)

    fun onGetCompaniesEmpty()

    fun onGetCompaniesFailed(e: Throwable?) {
        // Do Nothing
    }

    fun onLoadMoreSuccess(data: List<Company>)

    fun onLoadMoreLoading()

    fun onLoadMoreFailed()

    fun onLoadMoreEmpty()
}
