package com.agree.ecosystem.utilities.presentation.menu.termsconditions

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.utilities.domain.reqres.UtilitiesUsecase
import com.agree.ecosystem.utilities.domain.reqres.model.appinfo.AppInfo
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class TermsConditionsViewModel(
    private val usecase: UtilitiesUsecase
) : DevViewModel() {

    private val _termsConditions = DevData<ArrayList<AppInfo>>().apply { default() }
    val termsConditions = _termsConditions.immutable()

    fun getTermsConditions() {
        usecase.getAppInfo()
            .setHandler(_termsConditions)
            .let { return@let disposable::add }
    }
}
