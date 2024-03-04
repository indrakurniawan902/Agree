package com.agree.ecosystem.partnership.presentation.menu.statusregistration.detail

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class DetailStatusRegistrationObserver(
    private val view: DetailStatusRegistrationDataContract,
    private val viewModel: DetailStatusRegistrationViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.registrationStatus.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetRegistrationStatusDetailsLoading()
                is VmData.Success -> view.onGetRegistrationStatusDetailsSuccess(it.data)
                is VmData.Failure -> view.onGetRegistrationStatusDetailsFailed(it.throwable)
                is VmData.Empty -> view.onGetRegistrationStatusDetailsFailed(EmptyException())
            }
        }

        viewModel.registrationStatusTracking.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetRegistrationStatusTrackingLoading()
                is VmData.Success -> view.onGetRegistrationStatusTrackingSuccess(it.data)
                is VmData.Failure -> view.onGetRegistrationStatusTrackingFailed(it.throwable)
                is VmData.Empty -> view.onGetRegistrationStatusTrackingFailed(EmptyException())
            }
        }

        viewModel.cancelRegistration.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onCancelRegistrationLoading()
                is VmData.Success -> view.onCancelRegistrationSuccess(it.data)
                is VmData.Failure -> view.onCancelRegistrationFailed(it.throwable)
                is VmData.Empty -> view.onCancelRegistrationFailed(EmptyException())
            }
        }

        viewModel.detailFinalAssessment.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetDetailFinalAssessmentLoading()
                is VmData.Success -> view.onGetDetailFinalAssessmentSuccess(it.data)
                is VmData.Failure -> view.onGetDetailFinalAssessmentFailed(it.throwable)
                is VmData.Empty -> view.onGetDetailFinalAssessmentFailed(EmptyException())
            }
        }
    }
}
