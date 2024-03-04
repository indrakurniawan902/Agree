package com.agree.ecosystem.partnership.presentation.menu.detailvessel

import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel

interface DetailVesselDataContract {

    fun onGetListSubVesselLoading()

    fun onGetListSubVesselSuccess(data: List<SubVessel>)

    fun onGetListSubVesselEmpty()

    fun onGetListSubVesselFailed(e: Throwable?) {
        // Do Nothing
    }

    fun onLoadMoreSuccess(data: List<SubVessel>)

    fun onLoadMoreLoading()

    fun onLoadMoreFailed()

    fun onLoadMoreEmpty()
}
