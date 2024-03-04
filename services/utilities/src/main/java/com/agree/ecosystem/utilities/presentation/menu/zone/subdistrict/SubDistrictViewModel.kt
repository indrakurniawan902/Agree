package com.agree.ecosystem.utilities.presentation.menu.zone.subdistrict

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.utilities.domain.reqres.ZoneUsecase
import com.agree.ecosystem.utilities.domain.reqres.model.zone.SubDistrict
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class SubDistrictViewModel(
    private val zoneUsecase: ZoneUsecase
) : DevViewModel() {

    private val _subDistrict = DevData<List<SubDistrict>>().apply { default() }
    val subDistrict = _subDistrict.immutable()

    fun getSubDistrictsByDistrict(districtId: String) {
        zoneUsecase.getSubDistrictsByDistrict(districtId)
            .setHandler(_subDistrict)
            .let { return@let disposable::add }
    }
}
