package com.agree.ecosystem.monitoring.presentation.menu.totalactivitysop

import com.agree.ecosystem.monitoring.domain.reqres.model.totalactivitysop.TotalActivitySop
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselDataContract

interface TotalActivityDataContract : SubVesselDataContract {

    fun getTotalActivities(id: String)

    fun onGetTotalActivitySuccess(data: TotalActivitySop)

    fun onGetTotalActivityLoading()

    fun onGetTotalActivityEmpty()
}
