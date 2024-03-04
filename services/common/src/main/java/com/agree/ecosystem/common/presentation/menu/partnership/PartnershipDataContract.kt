package com.agree.ecosystem.common.presentation.menu.partnership

import com.agree.ecosystem.partnership.domain.reqres.model.companymember.CompanyMember

interface PartnershipDataContract {

    fun fetchCompanyMembers()

    fun fetchLoadMoreCompanyMembers()

    fun onGetCompanyMembersLoading()

    fun onGetCompanyMembersSuccess(data: List<CompanyMember>)

    fun onGetCompanyMembersEmpty()

    fun onGetCompanyMembersFailed(e: Throwable?)

    fun onLoadMoreSuccess(data: List<CompanyMember>)

    fun onLoadMoreLoading()

    fun onLoadMoreFailed()

    fun onLoadMoreEmpty()
}
