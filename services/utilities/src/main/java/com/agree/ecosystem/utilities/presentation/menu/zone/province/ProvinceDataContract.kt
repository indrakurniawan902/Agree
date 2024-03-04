package com.agree.ecosystem.utilities.presentation.menu.zone.province

import com.agree.ecosystem.utilities.domain.reqres.model.zone.Province

interface ProvinceDataContract {

    fun onLoading()

    fun onGetProvinceSuccess(data: List<Province>)

    fun onGetProvinceFailed(e: Throwable?)
}
