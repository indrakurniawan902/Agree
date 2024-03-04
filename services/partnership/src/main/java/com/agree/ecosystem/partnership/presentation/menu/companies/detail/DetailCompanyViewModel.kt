package com.agree.ecosystem.partnership.presentation.menu.companies.detail

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.partnership.domain.reqres.PartnershipUsecase
import com.agree.ecosystem.partnership.domain.reqres.model.company.Company
import com.agree.ecosystem.partnership.domain.reqres.model.validation.Validation
import com.agree.ecosystem.utilities.domain.reqres.model.commodity.Commodity
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class DetailCompanyViewModel(
    private val usecase: PartnershipUsecase
) : DevViewModel() {

    private val _company = DevData<Company>().apply { default() }
    val company = _company.immutable()

    private val _subSector = DevData<List<Commodity>>().apply { default() }
    val subSector = _subSector.immutable()

    private val _validation = DevData<Validation>().apply { default() }
    val validation = _validation.immutable()

    fun getDetailCompany(companyId: String) {
        usecase.getDetailCompany(companyId)
            .setHandler(_company)
            .let { return@let disposable::add }
    }

    fun getSubSectorList(companyId: String, subSectorId: String) {
        usecase.getSubSectorList(companyId, subSectorId)
            .setHandler(_subSector)
            .let { return@let disposable::add }
    }
    fun companyValidation(
        companyId: String
    ) {
        usecase.getValidationSubSector(companyId)
            .setHandler(_validation)
            .let { return@let disposable::add }
    }
}
