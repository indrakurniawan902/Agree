package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared

import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVessel

interface SharedComponentDataContract {
    fun onActionButtonTriggered(data: DetailSubVessel)
}
