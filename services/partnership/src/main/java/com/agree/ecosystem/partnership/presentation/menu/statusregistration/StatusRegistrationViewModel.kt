package com.agree.ecosystem.partnership.presentation.menu.statusregistration

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.partnership.data.reqres.model.registrationstatus.RegistrationStatusQuery
import com.agree.ecosystem.partnership.domain.reqres.PartnershipUsecase
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatus.RegistrationStatus
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class StatusRegistrationViewModel(
    private val usecase: PartnershipUsecase
) : DevViewModel() {

    var page = 1

    private val _registrationStatusList = DevData<List<RegistrationStatus>>().apply { default() }
    val registrationStatusList = _registrationStatusList.immutable()

    private val _loadMoreRegistrationStatusList = DevData<List<RegistrationStatus>>().apply { default() }
    val loadMoreRegistrationStatusList = _loadMoreRegistrationStatusList.immutable()

    fun getRegistrationStatusList(query: RegistrationStatusQuery) {
        page = 1
        usecase.getRegistrationStatusList(query, 1, 10)
            .setHandler(_registrationStatusList)
            .let { return@let disposable::add }
    }

    fun loadMoreRegistrationStatusList(query: RegistrationStatusQuery) {
        usecase.getRegistrationStatusList(query, page, 10)
            .setHandler(_loadMoreRegistrationStatusList)
            .let { return@let disposable::add }
    }
}
