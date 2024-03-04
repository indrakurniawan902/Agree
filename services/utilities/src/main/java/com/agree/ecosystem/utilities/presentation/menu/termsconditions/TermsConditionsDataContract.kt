package com.agree.ecosystem.utilities.presentation.menu.termsconditions

interface TermsConditionsDataContract {

    fun onGetTermsConditionsLoading()

    fun onGetTermsConditionsSuccess(data: String?)

    fun onGetTermsConditionsFailed(e: Throwable?)
}
