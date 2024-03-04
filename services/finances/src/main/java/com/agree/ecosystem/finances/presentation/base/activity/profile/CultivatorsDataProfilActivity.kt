package com.agree.ecosystem.finances.presentation.base.activity.profile

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.core.utils.base.AgrActivity
import com.agree.ecosystem.core.utils.utility.eventbus.AppEvent
import com.agree.ecosystem.core.utils.utility.extension.initGraph
import com.agree.ecosystem.finances.R
import com.agree.ecosystem.finances.databinding.ActivityCultivatorsDataProfilBinding
import com.agree.ecosystem.finances.domain.reqres.model.params.ProfileCultivatorParams
import com.agree.ecosystem.finances.presentation.menu.finance.cultivator.data.profilefarmer.ProfileCultivatorFragmentArgs
import com.agree.ecosystem.finances.utils.FinanceNameOfBundles.PARAM_TO_PROFILE_ACTIVITY
import com.agree.locales.presentation.delegation.LocaleActivityDelegate
import com.agree.locales.presentation.delegation.LocaleActivityDelegation
import com.oratakashi.viewbinding.core.tools.isNotNull
import kotlinx.coroutines.flow.collectLatest

class CultivatorsDataProfilActivity : AgrActivity<ActivityCultivatorsDataProfilBinding>(),
    LocaleActivityDelegate by LocaleActivityDelegation() {

    private var param: ProfileCultivatorParams? = null

    init {
        initLocale(this, super.getDelegate())
    }

    override fun getDelegate(): AppCompatDelegate {
        return getLocaleDelegate()
    }

    override fun initUI() {
        if (intent.getParcelableExtra<ProfileCultivatorParams>(PARAM_TO_PROFILE_ACTIVITY.param)
                .isNotNull()
        ) {
            param =
                intent.getParcelableExtra<ProfileCultivatorParams>(PARAM_TO_PROFILE_ACTIVITY.param) as ProfileCultivatorParams
        }

        super.initUI()
        lifecycleScope.launchWhenResumed {
            eventBus.events.collectLatest {
                when (it) {
                    AppEvent.FORCE_LOGOUT -> finish()
                    else -> return@collectLatest
                }
            }
        }

        if (param?.isFromLoan == true) {
            toDetailProfilFromLoan()
        } else {
            toDetailProfilElseFromLoan()
        }
    }

    private fun toDetailProfilFromLoan() {
        initGraph(
            R.id.nav_host_data_profil_cultivator,
            R.navigation.nav_cultivator_data_profile,
            ProfileCultivatorFragmentArgs(param!!).toBundle()
        ) {
            it.setStartDestination(R.id.profileCultivatorFragment)
        }
    }

    private fun toDetailProfilElseFromLoan() {
        initGraph(
            R.id.nav_host_data_profil_cultivator,
            R.navigation.nav_cultivator_data_profile
        )
    }
}