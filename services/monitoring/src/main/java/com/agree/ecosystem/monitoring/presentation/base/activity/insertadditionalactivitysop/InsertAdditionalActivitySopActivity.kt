package com.agree.ecosystem.monitoring.presentation.base.activity.insertadditionalactivitysop

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.core.utils.base.AgrActivity
import com.agree.ecosystem.core.utils.utility.eventbus.AppEvent
import com.agree.ecosystem.monitoring.databinding.ActivityInsertAdditionalActivitySopBinding
import com.agree.locales.presentation.delegation.LocaleActivityDelegate
import com.agree.locales.presentation.delegation.LocaleActivityDelegation
import kotlinx.coroutines.flow.collectLatest

class InsertAdditionalActivitySopActivity :
    AgrActivity<ActivityInsertAdditionalActivitySopBinding>(),
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
    }
}
