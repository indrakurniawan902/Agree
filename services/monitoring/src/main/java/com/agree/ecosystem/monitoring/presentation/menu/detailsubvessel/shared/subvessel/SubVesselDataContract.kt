package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel

import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVessel

interface SubVesselDataContract {
    fun onSubVesselIdChanged(id: String) {
        // Do Nothing
    }
    fun onSubVesselChanged(subVessel: DetailSubVessel?) {
        // Do Nothing
    }
    fun onStatusSubVesselChanged(status: String) {
        // Do Nothing
    }
}
