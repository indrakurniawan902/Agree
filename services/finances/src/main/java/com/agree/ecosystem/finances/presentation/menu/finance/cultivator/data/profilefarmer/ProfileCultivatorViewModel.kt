package com.agree.ecosystem.finances.presentation.menu.finance.cultivator.data.profilefarmer

import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.finances.domain.reqres.FinanceUsecase
import com.agree.ecosystem.finances.domain.reqres.model.profilefarmer.ProfileFarmer
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class ProfileCultivatorViewModel(private val usecase: FinanceUsecase) : DevViewModel() {

    private val _data = DevData<ProfileFarmer>().apply { default() }
    val data get() = _data

    fun fetchProfileCultivator(id: String) {
        usecase.fetchProfileCultivator(id)
            .setHandler(_data)
            .let { return@let disposable::add }
    }
}