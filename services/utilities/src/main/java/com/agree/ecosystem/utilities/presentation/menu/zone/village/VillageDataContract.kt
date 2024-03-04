package com.agree.ecosystem.utilities.presentation.menu.zone.village

import com.agree.ecosystem.utilities.domain.reqres.model.zone.Village

interface VillageDataContract {

    fun onLoading()

    fun onGetVillageSuccess(data: List<Village>)

    fun onGetVillageFailed(e: Throwable?)
}
