package com.agree.ecosystem.common.presentation.menu.partnership

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.partnership.data.reqres.model.companymember.CompanyMemberParams
import com.agree.ecosystem.partnership.domain.reqres.PartnershipUsecase
import com.agree.ecosystem.partnership.domain.reqres.model.companymember.CompanyMember
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class PartnershipViewModel(
    private val usecase: PartnershipUsecase,
) : DevViewModel() {

    var page = 1

    private val _companyMembers = DevData<List<CompanyMember>>().apply { default() }
    val companyMembers = _companyMembers.immutable()

    private val _loadMoreCompanyMembers = DevData<List<CompanyMember>>().apply { default() }
    val loadMoreCompanyMembers = _loadMoreCompanyMembers.immutable()

    fun getCompanyMembers(subSectorIds: List<String>, userId: String) {
        page = 1
        usecase.getCompanyMembers(CompanyMemberParams(1, 10, subSectorIds, userId))
            .setHandler(_companyMembers)
            .let { return@let disposable::add }
    }

    fun loadMoreCompanyMembers(subSectorIds: List<String>, userId: String) {
        usecase.getCompanyMembers(CompanyMemberParams(page, 10, subSectorIds, userId))
            .setHandler(_loadMoreCompanyMembers)
            .let { return@let disposable::add }
    }
}
