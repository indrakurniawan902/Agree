package com.agree.ecosystem.partnership.presentation.menu.statusregistration

import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatus.RegistrationStatus

interface StatusRegistrationDataContract {

    fun fetchRegistrationStatusList()

    fun onGetRegistrationStatusListLoading()

    fun onGetRegistrationStatusListSuccess(data: List<RegistrationStatus>)

    fun onRegistrationStatusListEmpty()

    fun onGetRegistrationStatusListFailed(e: Throwable?) {
        // Do Nothing
    }

    fun onLoadMoreSuccess(data: List<RegistrationStatus>)

    fun onLoadMoreLoading()

    fun onLoadMoreFailed()

    fun onLoadMoreEmpty()
}
