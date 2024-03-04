package com.agree.ecosystem.partnership.presentation.menu.registration

import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails.RegistrationStatusDetails

interface RegistrationBottomSheetDataContract {

    fun onRegistrationSuccess(data: RegistrationStatusDetails)

    fun onRegistrationLoading()

    fun onRegistrationFailed(e: Throwable?) {
        // Do Nothing
    }

    fun onRegistrationEmpty()
}
