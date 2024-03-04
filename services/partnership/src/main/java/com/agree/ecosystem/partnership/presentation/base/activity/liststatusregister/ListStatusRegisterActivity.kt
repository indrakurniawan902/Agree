package com.agree.ecosystem.partnership.presentation.base.activity.liststatusregister

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.core.utils.base.AgrActivity
import com.agree.ecosystem.core.utils.utility.eventbus.AppEvent
import com.agree.ecosystem.partnership.databinding.ActivityListStatusRegisterBinding
import com.agree.ecosystem.partnership.presentation.menu.registration.RegistrationBottomSheet
import com.agree.locales.presentation.delegation.LocaleActivityDelegate
import com.agree.locales.presentation.delegation.LocaleActivityDelegation
import kotlinx.coroutines.flow.collectLatest

class ListStatusRegisterActivity :
    AgrActivity<ActivityListStatusRegisterBinding>(),
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

        if (intent.hasExtra("fromRegister")) {
            RegistrationBottomSheet("").showNow(supportFragmentManager, "dialog")
        }
    }

    override fun onBackPressed() {
        if (intent.hasExtra("fromRegister")) {
            broadcastEvent { AppEvent.REGISTRATION_PARTNERSHIP_COMPLETE }
        }
        super.onBackPressed()
    }
}
