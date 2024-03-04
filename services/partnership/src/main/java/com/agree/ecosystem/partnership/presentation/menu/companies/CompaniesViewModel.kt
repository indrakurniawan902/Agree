package com.agree.ecosystem.partnership.presentation.menu.companies

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.partnership.data.reqres.model.company.CompanyParams
import com.agree.ecosystem.partnership.domain.reqres.PartnershipUsecase
import com.agree.ecosystem.partnership.domain.reqres.model.company.Company
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class CompaniesViewModel(
    private val usecase: PartnershipUsecase
) : DevViewModel() {

    var page = 1

    private val _companies = DevData<List<Company>>().apply { default() }
    val companies = _companies.immutable()

    private val _loadMoreCompanies = DevData<List<Company>>().apply { default() }
    val loadMoreCompanies = _loadMoreCompanies.immutable()

    fun getCompanies(subSectorId: String, userId: String, search: String) {
        page = 1
        usecase.getCompanyList(CompanyParams(1, 10, subSectorId, userId, search))
            .setHandler(_companies)
            .let { return@let disposable::add }
    }

    fun loadMoreCompanies(subSectorId: String, userId: String, search: String) {
        usecase.getCompanyList(CompanyParams(page, 10, subSectorId, userId, search))
            .setHandler(_loadMoreCompanies)
            .let { return@let disposable::add }
    }
}
