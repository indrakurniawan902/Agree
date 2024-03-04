package com.agree.ecosystem.utilities.presentation.menu.zone.district

import com.agree.ecosystem.utilities.domain.reqres.model.zone.District

interface DistrictDataContract {

    fun onLoading()

    fun onGetDistrictSuccess(data: List<District>)

    fun onGetDistrictFailed(e: Throwable?)
}
