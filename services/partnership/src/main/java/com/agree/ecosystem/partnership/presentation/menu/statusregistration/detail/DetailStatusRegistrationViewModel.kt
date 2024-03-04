package com.agree.ecosystem.partnership.presentation.menu.statusregistration.detail

import androidx.lifecycle.MutableLiveData
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.partnership.domain.reqres.PartnershipUsecase
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails.FinalAssessmentStatus
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails.RegistrationStatusDetails
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails.RegistrationStatusTracking
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel
import com.devbase.data.utilities.rx.transformers.flowableScheduler
import io.reactivex.Flowable
import io.reactivex.subscribers.DisposableSubscriber

class DetailStatusRegistrationViewModel(
    private val usecase: PartnershipUsecase
) : DevViewModel() {

    private val _registrationStatus = DevData<RegistrationStatusDetails>().apply { default() }
    val registrationStatus = _registrationStatus.immutable()

    private val _registrationStatusTracking =
        DevData<List<RegistrationStatusTracking>>().apply { default() }
    val registrationStatusTracking = _registrationStatusTracking.immutable()

    private val _detailFinalAssessment = DevData<List<FinalAssessmentStatus>>().apply { default() }
    val detailFinalAssessment = _detailFinalAssessment.immutable()

    private val _cancelRegistration = DevData<Any>().apply { default() }
    val cancelRegistration = _cancelRegistration.immutable()

    fun getData(submissionId: String) {
        Flowable.zip(
            usecase.getRegistrationStatusDetails(submissionId).compose(flowableScheduler()),
            usecase.getRegistrationStatusTracking(submissionId).compose(flowableScheduler())
        ) { detail, status ->
            _registrationStatus.success(detail)
            _registrationStatusTracking.success(status)
            true
        }.subscribeWith(object : DisposableSubscriber<Boolean>() {
            override fun onStart() {
                super.onStart()
                _registrationStatus.loading()
                _registrationStatusTracking.loading()
            }
            override fun onNext(t: Boolean?) {
                // Do Nothing
            }

            override fun onError(t: Throwable?) {
                t?.let {
                    _registrationStatus.fail(t, t.message)
                }
            }

            override fun onComplete() {
                _registrationStatus.default()
                _registrationStatusTracking.default()
            }
        }).let { return@let disposable::add }
    }

    fun getRegistrationStatusDetails(submissionId: String) {
        usecase.getRegistrationStatusDetails(submissionId)
            .setHandler(_registrationStatus)
            .let { return@let disposable::add }
    }

    fun getRegistrationStatusTracking(submissionId: String) {
        usecase.getRegistrationStatusTracking(submissionId)
            .setHandler(_registrationStatusTracking)
            .let { return@let disposable::add }
    }

    fun getDetailFinalAssessmentStatus(submissionId: String) {
        usecase.getDetailFinalAssessment(submissionId)
            .setHandler(_detailFinalAssessment)
            .let { return@let disposable::add }
    }

    val dataStatus = MutableLiveData<RegistrationStatusDetails>()
    fun setData(data: RegistrationStatusDetails) {
        dataStatus.value = data
    }

    fun cancelRegistration(submissionId: String) {
        usecase.cancelRegistration(submissionId)
            .setHandler(_cancelRegistration)
            .let { return@let disposable::add }
    }
}
