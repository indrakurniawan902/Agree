package com.agree.ecosystem.common.presentation.menu.monitoring.vessels

import com.agree.ecosystem.monitoring.domain.reqres.model.vessel.Vessel

interface VesselDataContract {

    fun fetchListVessel()

    fun fetchLoadMoreVessel()

    fun onGetListVesselSuccess(data: List<Vessel>)

    fun onGetListVesselLoading()

    fun onGetListVesselEmpty()

    fun onGetListVesselFailed(e: Throwable?)

    fun onLoadMoreVesselSuccess(data: List<Vessel>)

    fun onLoadMoreVesselLoading()

    fun onLoadMoreVesselEmpty()

    fun onLoadMoreVesselFailed()
}
