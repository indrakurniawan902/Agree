package com.agree.ecosystem.core.utils.base

import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.agree.ecosystem.core.analytics.AgrAnalytics
import com.agree.ecosystem.core.utils.utility.eventbus.AppEvent
import com.agree.ecosystem.core.utils.utility.eventbus.EventBus
import com.agree.ecosystem.core.utils.utility.eventbus.EventBusEngine
import com.devbase.presentation.viewbinding.DevFragment
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * Base Fragment without Analytics Record View
 * @author : @telkomdev-abdul
 */
abstract class AgrChildFragment<T : ViewBinding> : DevFragment<T>(), EventBusEngine {

    open val analytics by inject<AgrAnalytics>()
    override val eventBus: EventBus by inject()

    override fun broadcastEvent(event: () -> AppEvent?) {
        event.invoke()?.let { lifecycleScope.launch { eventBus.invokeEvent(it) } }
    }
}
