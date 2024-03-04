package com.agree.ecosystem.utilities.presentation.menu.zone.province

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.utilities.domain.reqres.ZoneUsecase
import com.agree.ecosystem.utilities.domain.reqres.model.zone.Province
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class ProvinceViewModel(
    private val zoneUsecase: ZoneUsecase
) : DevViewModel() {

    private val _provinces = DevData<List<Province>>().apply { default() }
    val provinces = _provinces.immutable()

    fun getAllProvinces() {
        zoneUsecase.getAllProvinces()
            .setHandler(_provinces)
            .let { return@let disposable::add }
    }
}
