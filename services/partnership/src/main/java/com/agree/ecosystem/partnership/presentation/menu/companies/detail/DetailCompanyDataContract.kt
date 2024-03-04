package com.agree.ecosystem.partnership.presentation.menu.companies.detail

import com.agree.ecosystem.partnership.domain.reqres.model.company.Company
import com.agree.ecosystem.partnership.domain.reqres.model.validation.Validation
import com.agree.ecosystem.utilities.domain.reqres.model.commodity.Commodity

interface DetailCompanyDataContract {

    fun onGetDetailCompanyLoading()

    fun onGetDetailCompanySuccess(data: Company)

    fun onGetDetailCompanyFailed(e: Throwable?) {
        // Do Nothing
    }

    fun onGetSubSectorLoading()

    fun onGetSubSectorSuccess(data: List<Commodity>)

    fun onGetSubSectorFailed(e: Throwable?) {
        // Do Nothing
    }

    fun onValidationLoading()

    fun onValidationSuccess(data: Validation)

    fun onValidationEmpty()

    fun onValidationFailed(e: Throwable?) {
        // Do Nothing
    }
}
