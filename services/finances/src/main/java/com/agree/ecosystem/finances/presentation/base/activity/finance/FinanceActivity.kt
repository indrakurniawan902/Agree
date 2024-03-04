package com.agree.ecosystem.finances.presentation.base.activity.finance

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.core.utils.base.AgrActivity
import com.agree.ecosystem.core.utils.utility.eventbus.AppEvent
import com.agree.ecosystem.core.utils.utility.extension.initGraph
import com.agree.ecosystem.finances.R
import com.agree.ecosystem.finances.databinding.ActivityFinanceBinding
import com.agree.ecosystem.finances.presentation.menu.finance.FinanceFragmentArgs
import com.agree.locales.presentation.delegation.LocaleActivityDelegate
import com.agree.locales.presentation.delegation.LocaleActivityDelegation
import kotlinx.coroutines.flow.collectLatest

class FinanceActivity : AgrActivity<ActivityFinanceBinding>(),
    LocaleActivityDelegate by LocaleActivityDelegation() {

    init {
        initLocale(this, super.getDelegate())
    }

    override fun getDelegate(): AppCompatDelegate {
        return getLocaleDelegate()
    }

    override fun initUI() {

        val initTab = intent.getIntExtra("initTab", 0)
        val activeLoanDetail = intent.getStringExtra("activeLoanDetail")
        super.initUI()
        lifecycleScope.launchWhenResumed {
            eventBus.events.collectLatest {
                when (it) {
                    AppEvent.FORCE_LOGOUT -> finish()
                    else -> return@collectLatest
                }
            }
        }

        if (activeLoanDetail?.isNotEmpty() == true) {
            initGraph(
                R.id.nav_host_activity_finance,
                R.navigation.nav_finance,
            ) {
                it.setStartDestination(R.id.loanActiveDetailFragment)
            }
        } else {
            initGraph(
                R.id.nav_host_activity_finance,
                R.navigation.nav_finance,
                FinanceFragmentArgs(initTab).toBundle()
            )
        }
    }
}
