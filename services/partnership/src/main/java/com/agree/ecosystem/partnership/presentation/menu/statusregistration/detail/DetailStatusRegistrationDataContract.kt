package com.agree.ecosystem.partnership.presentation.menu.statusregistration.detail

import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails.FinalAssessmentStatus
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails.RegistrationStatusDetails
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails.RegistrationStatusTracking

interface DetailStatusRegistrationDataContract {

    fun getData()

    fun onGetRegistrationStatusDetailsLoading()

    fun onGetRegistrationStatusDetailsSuccess(data: RegistrationStatusDetails)

    fun onGetRegistrationStatusDetailsFailed(e: Throwable?)

    fun onGetRegistrationStatusTrackingLoading()

    fun onGetRegistrationStatusTrackingSuccess(data: List<RegistrationStatusTracking>)

    fun onGetRegistrationStatusTrackingFailed(e: Throwable?)

    fun doCancelRegistration()

    fun onCancelRegistrationLoading()

    fun onCancelRegistrationSuccess(data: Any)

    fun onCancelRegistrationFailed(e: Throwable?)

    fun showConfirmationDialog()

    fun getDetailFinalAssessment()

    fun onGetDetailFinalAssessmentLoading()

    fun onGetDetailFinalAssessmentSuccess(data: List<FinalAssessmentStatus>)

    fun onGetDetailFinalAssessmentFailed(e: Throwable?)
}
