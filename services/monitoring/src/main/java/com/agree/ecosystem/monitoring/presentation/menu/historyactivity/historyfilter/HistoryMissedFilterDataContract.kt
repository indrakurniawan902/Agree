package com.agree.ecosystem.monitoring.presentation.menu.historyactivity.historyfilter

import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitiesSopMissed
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselDataContract

interface HistoryMissedFilterDataContract : SubVesselDataContract {

    fun getListActivity(id: String)
    fun getLoadMoreActivity(id: String)

    fun onGetListActivityLoading()
    fun onGetListActivitySuccess(data: List<ActivitiesSopMissed>)
    fun onGetListActivityFailed()
    fun onGetListActivityEmpty()

    fun onLoadMoreSuccess(data: List<ActivitiesSopMissed>)
    fun onLoadMoreLoading()
    fun onLoadMoreFailed()
    fun onLoadMoreEmpty()
}
