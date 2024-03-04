package com.agree.ecosystem.core.utils.base

import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.agree.ecosystem.core.analytics.AgrAnalytics
import com.agree.ecosystem.core.analytics.constant.FirebaseUserProperty
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.eventbus.AppEvent
import com.agree.ecosystem.core.utils.utility.eventbus.EventBus
import com.devbase.presentation.viewbinding.DevActivity
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

abstract class AgrActivity<T : ViewBinding> : DevActivity<T>() {

    open val analytics by inject<AgrAnalytics>()
    open val eventBus: EventBus by inject()

    override fun initUI() {
        super.initUI()
        initAnalytics()
    }

    private fun initAnalytics() {
        val pref: AgrPrefUsecase by inject()
        analytics.logEvent(
            FirebaseAnalytics.Event.SCREEN_VIEW,
            bundleOf(
                FirebaseAnalytics.Param.SCREEN_NAME to javaClass.simpleName,
                FirebaseAnalytics.Param.SCREEN_CLASS to javaClass.canonicalName,
                FirebaseUserProperty.USERNAME to pref.userName
            )
        )
    }

    open fun broadcastEvent(event: () -> AppEvent?) {
        event.invoke()?.let { lifecycleScope.launch { eventBus.invokeEvent(it) } }
    }
}
