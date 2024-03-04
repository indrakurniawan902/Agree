package com.agree.ecosystem.monitoring.presentation.menu.detailarea

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.monitoring.data.reqres.model.subvessel.SubVesselParams
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import com.agree.ecosystem.monitoring.domain.reqres.model.vessel.Vessel
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class MonitoringDetailViewModel(
    private val usecase: MonitoringUseCase
) : DevViewModel() {

    var page = 1

    private val _subVessel = DevData<ArrayList<SubVessel>>().apply { default() }
    val subVessel = _subVessel.immutable()

    private val _vessel = DevData<Vessel>().apply { default() }
    val vessel = _vessel.immutable()

    private val _loadMoreSubVessel = DevData<ArrayList<SubVessel>>().apply { default() }
    val loadMoreSubVessel = _loadMoreSubVessel.immutable()

    fun getDetailVessel(id: String) {
        usecase.getVessel(id)
            .setHandler(_vessel)
            .let { return@let disposable::add }
    }

    fun getListSubVessel(
        id: String,
        search: String,
        filter: String,
        hasSmartFarming: Boolean
    ) {
        page = 1
        usecase.getListSubVessel(SubVesselParams(10, 1, search, id, filter, hasSmartFarming))
            .setHandler(_subVessel)
            .let { return@let disposable::add }
    }

    fun loadMoreSubVessel(
        id: String,
        search: String,
        filter: String,
        hasSmartFarming: Boolean
    ) {
        usecase.getListSubVessel(SubVesselParams(10, page, search, id, filter, hasSmartFarming))
            .setHandler(_loadMoreSubVessel)
            .let { return@let disposable::add }
    }
}
