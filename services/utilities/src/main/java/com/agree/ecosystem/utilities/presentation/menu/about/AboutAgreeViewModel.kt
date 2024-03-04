package com.agree.ecosystem.utilities.presentation.menu.about

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.utilities.domain.reqres.UtilitiesUsecase
import com.agree.ecosystem.utilities.domain.reqres.model.appinfo.AppInfo
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class AboutAgreeViewModel(
    private val usecase: UtilitiesUsecase
) : DevViewModel() {

    private val _aboutAgree = DevData<ArrayList<AppInfo>>().apply { default() }
    val aboutAgree = _aboutAgree.immutable()

    fun getAboutAgree() {
        usecase.getAppInfo()
            .setHandler(_aboutAgree)
            .let { return@let disposable::add }
    }
}
