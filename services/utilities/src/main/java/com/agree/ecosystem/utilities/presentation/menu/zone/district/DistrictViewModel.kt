package com.agree.ecosystem.utilities.presentation.menu.zone.district

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.utilities.domain.reqres.ZoneUsecase
import com.agree.ecosystem.utilities.domain.reqres.model.zone.District
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class DistrictViewModel(
    private val zoneUsecase: ZoneUsecase
) : DevViewModel() {

    private val _district = DevData<List<District>>().apply { default() }
    val district = _district.immutable()

    fun getDistrictsByProvince(provinceId: String) {
        zoneUsecase.getDistrictsByProvince(provinceId)
            .setHandler(_district)
            .let { return@let disposable::add }
    }
}
