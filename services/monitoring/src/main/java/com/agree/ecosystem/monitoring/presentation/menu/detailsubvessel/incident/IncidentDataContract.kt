package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident

import com.agree.ecosystem.monitoring.domain.reqres.model.incident.Incident
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.SharedComponentDataContract
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselDataContract

interface IncidentDataContract :
    SubVesselDataContract,
    SharedComponentDataContract {

    fun fetchIncidentList()

    fun onIncidentLoading()

    fun onIncidentSuccess(data: List<Incident>)

    fun onIncidentFailed(e: Throwable?)

    fun onIncidentEmpty()

    fun fetchLoadMoreIncident()

    fun onLoadMoreIncidentLoading()

    fun onLoadMoreIncidentSuccess(data: List<Incident>)

    fun onLoadMoreIncidentFailed(e: Throwable?)

    fun onLoadMoreIncidentEmpty()
}
