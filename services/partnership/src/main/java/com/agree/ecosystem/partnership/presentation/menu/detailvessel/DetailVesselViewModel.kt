package com.agree.ecosystem.partnership.presentation.menu.detailvessel

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import com.agree.ecosystem.partnership.domain.reqres.PartnershipUsecase
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class DetailVesselViewModel(
    private val usecase: PartnershipUsecase
) : DevViewModel() {

    var page = 1

    private val _listSubVessel = DevData<List<SubVessel>>().apply { default() }
    val listSubVessel = _listSubVessel.immutable()

    private val _loadMoreListSubVessel = DevData<List<SubVessel>>().apply { default() }
    val loadMoreListSubVessel = _loadMoreListSubVessel.immutable()

    fun getListSubVessel(vesselId: String) {
        page = 1
        usecase.getListSubVessel(vesselId, 1, 10)
            .setHandler(_listSubVessel)
            .let { return@let disposable::add }
    }

    fun loadMoreListSubVessel(vesselId: String) {
        usecase.getListSubVessel(vesselId, page, 10)
            .setHandler(_loadMoreListSubVessel)
            .let { return@let disposable::add }
    }
}
