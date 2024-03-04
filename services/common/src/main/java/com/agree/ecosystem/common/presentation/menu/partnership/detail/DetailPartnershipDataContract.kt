package com.agree.ecosystem.common.presentation.menu.partnership.detail

import com.agree.ecosystem.partnership.domain.reqres.model.companymember.CompanyMemberDetail

interface DetailPartnershipDataContract {

    fun fetchCompanyMemberDetails()

    fun onGetCompanyMemberDetailsLoading()

    fun onGetCompanyMemberDetailsSuccess(data: CompanyMemberDetail)

    fun onGetCompanyMemberDetailsFailed(e: Throwable?)
}
