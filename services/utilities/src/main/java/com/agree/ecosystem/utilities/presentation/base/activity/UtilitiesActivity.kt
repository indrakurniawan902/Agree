package com.agree.ecosystem.utilities.presentation.base.activity

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.core.utils.base.AgrActivity
import com.agree.ecosystem.core.utils.utility.eventbus.AppEvent
import com.agree.ecosystem.core.utils.utility.extension.initGraph
import com.agree.ecosystem.utilities.R
import com.agree.ecosystem.utilities.databinding.ActivityUtilitiesBinding
import com.agree.ecosystem.utilities.presentation.navigation.NavigationScreen
import com.agree.locales.presentation.delegation.LocaleActivityDelegate
import com.agree.locales.presentation.delegation.LocaleActivityDelegation
import com.oratakashi.viewbinding.core.tools.toast
import kotlinx.coroutines.flow.collectLatest

class UtilitiesActivity :
    AgrActivity<ActivityUtilitiesBinding>(),
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

        val startScreen = intent.getStringExtra("screen")

        when (NavigationScreen.Parser.parse(startScreen)) {
            NavigationScreen.About -> {
                initGraph(
                    R.id.nav_host_utilities,
                    R.navigation.nav_utilities
                )
            }
            NavigationScreen.Help -> {
                initGraph(
                    R.id.nav_host_utilities,
                    R.navigation.nav_utilities
                ) {
                    it.setStartDestination(R.id.helpFragment)
                }
            }
            NavigationScreen.Tnc -> {
                initGraph(
                    R.id.nav_host_utilities,
                    R.navigation.nav_utilities
                ) {
                    it.setStartDestination(R.id.termsConditionsFragment)
                }
            }
            null -> {
                toast("Something Wrong!")
                finish()
            }
        }
    }
}
