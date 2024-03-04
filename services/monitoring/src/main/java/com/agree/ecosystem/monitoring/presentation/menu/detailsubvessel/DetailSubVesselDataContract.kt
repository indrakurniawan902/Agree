package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel

import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVessel
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselDataContract

interface DetailSubVesselDataContract : SubVesselDataContract {

    fun onGetDetailSubVesselLoading()

    fun onGetDetailSubVesselSuccess(data: DetailSubVessel)

    fun onGetDetailSubVesselFailed(e: Throwable?)

    fun doEndCycle()

    fun onEndCycleLoading()

    fun onEndCycleSuccess(data: DetailSubVessel)

    fun onEndCycleFailed(e: Throwable?)

    fun showConfirmationDialog()
}
