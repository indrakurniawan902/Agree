package com.agree.ecosystem.monitoring.presentation.menu.historyactivity.historyfilter

import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitySopGroupByDate
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselDataContract

interface HistoryFilterDataContract : SubVesselDataContract {
    fun getListActivity(id: String)
    fun getLoadMoreActivity(id: String)

    fun onGetListActivityLoading()
    fun onGetListActivitySuccess(data: List<ActivitySopGroupByDate>)
    fun onGetListActivityFailed()
    fun onGetListActivityEmpty()

    fun onLoadMoreSuccess(data: List<ActivitySopGroupByDate>)
    fun onLoadMoreLoading()
    fun onLoadMoreFailed()
    fun onLoadMoreEmpty()
}
