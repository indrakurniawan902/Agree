package com.agree.ecosystem.common.presentation.menu.monitoring.vessels

import com.agree.ecosystem.common.data.reqres.model.monitoring.VesselParams
import com.agree.ecosystem.common.domain.reqres.MonitoringUsecase
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.monitoring.domain.reqres.model.vessel.Vessel
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class VesselViewModel(
    private val usecase: MonitoringUsecase,
) : DevViewModel() {

    var page = 1

    private val _vessel = DevData<List<Vessel>>().apply { default() }
    val vessel = _vessel.immutable()

    private val _loadMoreVessel = DevData<List<Vessel>>().apply { default() }
    val loadMoreVessel = _loadMoreVessel.immutable()

    fun getListVessel(userId: String, vesselName: String, subSectorId: String) {
        page = 1
        usecase.getListVessel(VesselParams(page = 1, quantity = 10, vesselName = vesselName, subSectorId = subSectorId, userId = userId))
            .setHandler(_vessel)
            .let { return@let disposable::add }
    }

    fun loadMoreVessel(userId: String, vesselName: String, subSectorId: String) {
        usecase.getListVessel(VesselParams(page = page, quantity = 10, vesselName = vesselName, subSectorId = subSectorId, userId = userId))
            .setHandler(_loadMoreVessel)
            .let { return@let disposable::add }
    }
}
