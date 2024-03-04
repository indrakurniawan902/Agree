package com.agree.ecosystem.splash.presentation.menu.updater

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.utilities.domain.reqres.UtilitiesUsecase
import com.agree.ecosystem.utilities.domain.reqres.model.appinfo.AppInfo
import com.agree.locales.domain.LocaleUsecase
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel
import com.devbase.data.utilities.rx.transformers.flowableScheduler
import io.reactivex.Flowable

class UpdaterViewModel(
    private val usecase: UtilitiesUsecase,
    private val localeUsecase: LocaleUsecase
) : DevViewModel() {

    private val _appInfo = DevData<ArrayList<AppInfo>>().apply { default() }

    val appInfo = _appInfo.immutable()

    fun getAppInfo() {
        Flowable.zip(
            usecase.initRemoteConfig().compose(flowableScheduler()).onErrorReturn { false },
            usecase.getAppInfo(isOnlinePriority = true).compose(flowableScheduler()).onErrorReturn { null },
            localeUsecase.getResources().compose(flowableScheduler()).onErrorReturn { null },
        ) { _, appInfo, _ ->
            appInfo
        }
            .setHandler(_appInfo)
            .let { return@let disposable::add }
    }
}
