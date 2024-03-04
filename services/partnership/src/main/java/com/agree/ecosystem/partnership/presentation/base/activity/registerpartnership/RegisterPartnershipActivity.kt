package com.agree.ecosystem.partnership.presentation.base.activity.registerpartnership

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.core.utils.base.AgrActivity
import com.agree.ecosystem.core.utils.utility.eventbus.AppEvent
import com.agree.ecosystem.core.utils.utility.extension.initGraph
import com.agree.ecosystem.partnership.R
import com.agree.ecosystem.partnership.databinding.ActivityRegisterPartnershipBinding
import com.agree.ecosystem.partnership.presentation.menu.companies.detail.DetailCompanyFragmentArgs
import com.agree.locales.presentation.delegation.LocaleActivityDelegate
import com.agree.locales.presentation.delegation.LocaleActivityDelegation
import kotlinx.coroutines.flow.collectLatest

class RegisterPartnershipActivity :
    AgrActivity<ActivityRegisterPartnershipBinding>(),
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

        val idCompany: String? = intent.getStringExtra("id")

        if (idCompany != null) {
            initGraph(
                R.id.nav_host_register_partnership,
                R.navigation.nav_register_partnership,
                DetailCompanyFragmentArgs(
                    companyId = idCompany
                ).toBundle()
            ) {
                it.setStartDestination(R.id.detailCompanyFragment)
            }
        } else {
            initGraph(
                R.id.nav_host_register_partnership,
                R.navigation.nav_register_partnership
            )
        }
    }
}
