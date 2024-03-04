package com.agree.ecosystem.monitoring.presentation.base.activity.activitysop

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.core.utils.base.AgrActivity
import com.agree.ecosystem.core.utils.utility.eventbus.AppEvent
import com.agree.ecosystem.core.utils.utility.extension.initGraph
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.ActivityActivitySopBinding
import com.agree.locales.presentation.delegation.LocaleActivityDelegate
import com.agree.locales.presentation.delegation.LocaleActivityDelegation
import kotlinx.coroutines.flow.collectLatest

class ActivitySopActivity :
    AgrActivity<ActivityActivitySopBinding>(),
    LocaleActivityDelegate by LocaleActivityDelegation() {

    init {
        initLocale(this, super.getDelegate())
    }

    override fun getDelegate(): AppCompatDelegate {
        return getLocaleDelegate()
    }

    override fun initUI() {
        super.initUI()
        lifecycleScope.launchWhenResumed {
            eventBus.events.collectLatest {
                when (it) {
                    AppEvent.FORCE_LOGOUT -> {
                        finish()
                    }
                    else -> return@collectLatest
                }
            }
        }

        initGraph(
            R.id.nav_host_activity_sop,
            R.navigation.nav_activity_sop
        ) {
            if (intent.getIntExtra("destinationId", 0) > 1) {
                it.setStartDestination(intent.getIntExtra("destinationId", 0))
            }
        }
    }
}
