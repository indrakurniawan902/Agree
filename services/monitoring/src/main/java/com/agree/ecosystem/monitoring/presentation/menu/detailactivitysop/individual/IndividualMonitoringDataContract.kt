package com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop.individual

import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.SopActivityDetail
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesselIndividual.IndividualSubVessel

interface IndividualMonitoringDataContract {
    fun fetchIndividualMonitoring()
    fun fetchLoadMoreIndividualMonitoring() {}

    fun onIndividualLoading()
    fun onIndividualSuccess(data: List<IndividualSubVessel>)
    fun onIndividualEmpty()
    fun onIndividualError(e: Throwable?)

    fun onLoadMoreIndividualLoading() {}
    fun onLoadMoreIndividualSuccess(data: List<IndividualSubVessel>) {}
    fun onLoadMoreIndividualEmpty() {}
    fun onLoadMoreIndividualError(e: Throwable?) {}

    fun onEntryPointLoading() {}
    fun onEntryPointSuccess(data: SopActivityDetail) {}
    fun onEntryPointError(e: Throwable?) {}
}
