package com.agree.ecosystem.monitoring.presentation.menu.detailarea

import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import com.agree.ecosystem.monitoring.domain.reqres.model.vessel.Vessel

interface MonitoringDetailDataContract {
    fun fetchListSubVessel()

    fun fetchVessel()

    fun fetchLoadMoreSubVessel()

    fun onGetDetailVesselSuccess(data: Vessel)

    fun onGetDetailVesselLoading()

    fun onGetDetailVesselEmpty()

    fun onGetDetailVesselFailed(e: Throwable?)

    fun onGetListSubVesselSuccess(data: List<SubVessel>)

    fun onGetListSubVesselLoading()

    fun onGetListSubVesselEmpty()

    fun onGetListSubVesselFailed(e: Throwable?)

    fun onLoadMoreSubVesselSuccess(data: List<SubVessel>)

    fun onLoadMoreSubVesselLoading()

    fun onLoadMoreSubVesselEmpty()

    fun onLoadMoreSubVesselFailed()
}
