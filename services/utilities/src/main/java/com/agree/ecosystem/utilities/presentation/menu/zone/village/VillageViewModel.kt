package com.agree.ecosystem.utilities.presentation.menu.zone.village

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.utilities.domain.reqres.ZoneUsecase
import com.agree.ecosystem.utilities.domain.reqres.model.zone.Village
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class VillageViewModel(
    private val zoneUsecase: ZoneUsecase
) : DevViewModel() {

    private val _village = DevData<List<Village>>().apply { default() }
    val village = _village.immutable()

    fun getVillagesBySubDistrict(subDistrictId: String) {
        zoneUsecase.getVillagesBySubDistrict(subDistrictId)
            .setHandler(_village)
            .let { return@let disposable::add }
    }
}
