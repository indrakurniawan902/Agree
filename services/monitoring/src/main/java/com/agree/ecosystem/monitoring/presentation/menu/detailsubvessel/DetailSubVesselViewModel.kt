package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVessel
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class DetailSubVesselViewModel(
    private val usecase: MonitoringUseCase
) : DevViewModel() {

    private val _subVessel = DevData<DetailSubVessel>().apply { default() }
    val subVessel = _subVessel.immutable()

    private val _endCycle = DevData<DetailSubVessel>().apply { default() }
    val endCycle = _endCycle.immutable()

    fun getDetailSubVessel(id: String) {
        usecase.getDetailSubVessel(id).setHandler(_subVessel)
            .let { return@let disposable::add }
    }

    fun endCycleSubVessel(subVesselId: String) {
        usecase.deactivateSubVessel(subVesselId)
            .setHandler(_endCycle)
            .let { return@let disposable::add }
    }
}
