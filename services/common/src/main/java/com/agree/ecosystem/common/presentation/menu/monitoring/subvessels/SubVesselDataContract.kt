package com.agree.ecosystem.common.presentation.menu.monitoring.subvessels

import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel

interface SubVesselDataContract {

    fun fetchListSubVessel()

    fun fetchLoadMoreSubVessel()

    fun onGetListSubVesselSuccess(data: List<SubVessel>)

    fun onGetListSubVesselLoading()

    fun onGetListSubVesselEmpty()

    fun onGetListSubVesselFailed(e: Throwable?)

    fun onLoadMoreSubVesselSuccess(data: List<SubVessel>)

    fun onLoadMoreSubVesselLoading()

    fun onLoadMoreSubVesselEmpty()

    fun onLoadMoreSubVesselFailed()

    fun showSmartFarmingCoachmark()
}
