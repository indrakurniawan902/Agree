package com.agree.ecosystem.monitoring.presentation.menu.phaseactivity

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.agree.ecosystem.monitoring.domain.reqres.model.crudengineparams.CrudEngineParams
import com.agree.ecosystem.monitoring.domain.reqres.model.phaseactivity.PhaseActivity
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class PhaseActivityViewModel(
    private val useCase: MonitoringUseCase
) : DevViewModel() {

    private val _phaseActivityList = DevData<List<PhaseActivity>>().apply { default() }
    val phaseActivityList = _phaseActivityList.immutable()

    fun getPhaseActivityList(subVesselId: String) {
        useCase.getListPhaseActivity(
            CrudEngineParams(
                "list_phase",
                "s.id = '$subVesselId' AND pa.\"id\" IS NOT NULL AND p.is_deleted = false GROUP BY p.ID, c.data ->> 'incident_category', spa.type ORDER BY p.order::int",
                subVesselId = subVesselId
            )
        )
            .setHandler(_phaseActivityList)
            .let { return@let disposable::add }
    }
}
