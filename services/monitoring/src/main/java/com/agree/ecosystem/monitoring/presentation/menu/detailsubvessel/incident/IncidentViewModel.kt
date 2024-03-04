package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.monitoring.data.reqres.model.incident.IncidentParams
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.agree.ecosystem.monitoring.domain.reqres.model.incident.Incident
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class IncidentViewModel(
    private val usecase: MonitoringUseCase,
) : DevViewModel() {

    var page = 1

    private val _incident = DevData<ArrayList<Incident>>().apply { default() }
    val incident = _incident.immutable()

    private val _loadMoreIncident = DevData<ArrayList<Incident>>().apply { default() }
    val loadMoreIncident = _loadMoreIncident.immutable()

    fun getListIncident(subVesselId: String) {
        page = 1
        usecase.getListIncident(IncidentParams(page = page, 10, subVesselId))
            .setHandler(_incident)
            .let { return@let disposable::add }
    }

    fun loadMoreListIncident(subVesselId: String) {
        usecase.getListIncident(IncidentParams(page, 10, subVesselId))
            .setHandler(_loadMoreIncident)
            .let { return@let disposable::add }
    }
}
