package com.agree.ecosystem.common.presentation.menu.monitoring.subvessels

import com.agree.ecosystem.common.data.reqres.model.monitoring.SubVesselParams
import com.agree.ecosystem.common.domain.reqres.MonitoringUsecase
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class SubVesselViewModel(
    private val usecase: MonitoringUsecase,
) : DevViewModel() {

    var page = 1

    private val _subVessel = DevData<List<SubVessel>>().apply { default() }
    val subVessel = _subVessel.immutable()

    private val _loadMoreSubVessel = DevData<List<SubVessel>>().apply { default() }
    val loadMoreSubVessel = _loadMoreSubVessel.immutable()

    fun getListSubVessel(
        userId: String,
        search: String,
        subSectorId: String,
        hasSmartFarm: Boolean
    ) {
        page = 1
        usecase.getListSubVessel(
            SubVesselParams(
                10,
                1,
                search,
                "",
                subSectorId,
                userId,
                hasSmartFarm
            )

        )
            .setHandler(_subVessel)
            .let { return@let disposable::add }
    }

    fun loadMoreSubVessel(
        userId: String,
        search: String,
        subSectorId: String,
        hasSmartFarm: Boolean
    ) {
        usecase.getListSubVessel(
            SubVesselParams(
                10,
                page,
                search,
                "",
                subSectorId,
                userId,
                hasSmartFarm
            )
        )
            .setHandler(_loadMoreSubVessel)
            .let { return@let disposable::add }
    }

    fun generateDummySmartFarmingData(): SubVessel {
        return SubVessel(
            "",
            "",
            "",
            "",
            "PT. Agreeculture",
            "",
            "",
            "",
            "Lele Jumbo",
            "",
            "Suka Bumi",
            "Kolam Lele 2",
            "",
            SubVessel.Status.ACTIVE,
            "",
            "",
            "50",
            "",
            "",
            "",
            "",
            "",
            "",
            "Didin Samsudin",
            "",
            "",
            0.0,
            "Area Dekat Sungai",
            "",
            "Kolam Lele 2",
            true

//            "",
//            "",
//            "",
//            "Lele Jumbo",
//            "",
//            "",
//            "",
//            "PT. Agreeculture",
//            "",
//            "",
//            "",
//            0,
//            "",
//            "",
//            "Sukabumi",
//            "",
//            "",
//            "",
//            "",
//            "",
//            "",
//            "50",
//            SubVessel.Status.ACTIVE,
//            "",
//            "",
//            "Kolam Lele 2",
//            "",
//            "",
//            "",
//            "Area Dekat Sungai",
//            "Didin Samsudin",
//            true
        )
    }
}
