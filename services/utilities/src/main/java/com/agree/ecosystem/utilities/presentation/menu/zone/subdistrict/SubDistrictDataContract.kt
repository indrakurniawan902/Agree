package com.agree.ecosystem.utilities.presentation.menu.zone.subdistrict

import com.agree.ecosystem.utilities.domain.reqres.model.zone.SubDistrict

interface SubDistrictDataContract {

    fun onLoading()

    fun onGetSubDistrictSuccess(data: List<SubDistrict>)

    fun onGetSubDistrictFailed(e: Throwable?)
}
