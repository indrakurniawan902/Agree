package com.agree.ecosystem.common.presentation.menu.partnership.detail

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.partnership.domain.reqres.PartnershipUsecase
import com.agree.ecosystem.partnership.domain.reqres.model.companymember.CompanyMemberDetail
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class DetailPartnershipViewModel(
    private val usecase: PartnershipUsecase,
) : DevViewModel() {

    private val _companyMemberDetails = DevData<CompanyMemberDetail>().apply { default() }
    val companyMemberDetails = _companyMemberDetails.immutable()

    fun getCompanyMemberDetails(companyMemberId: String) {
        usecase.getCompanyMemberDetails(companyMemberId)
            .setHandler(_companyMemberDetails)
            .let { return@let disposable::add }
    }
}
