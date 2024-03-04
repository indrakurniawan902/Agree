package com.agree.ecosystem.finances.presentation.menu.finance.cultivator.data.profilefarmer

import com.agree.ecosystem.finances.domain.reqres.model.profilefarmer.ProfileFarmer

interface ProfileCultivatorDataContract {

    fun fetchProfileCultivator(id: String)

    fun profileCultivatorOnLoading()

    fun profileCultivatorOnSuccess(data: ProfileFarmer)

    fun profileCultivatorOnEmpty(data: ProfileFarmer?)

    fun profileCultivatorOnError(e: Throwable?)

}