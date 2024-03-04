package com.agree.ecosystem.monitoring.presentation.base.activity.detailsubvassel

import android.util.TypedValue
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.core.utils.base.AgrActivity
import com.agree.ecosystem.core.utils.utility.StatusBarColors
import com.agree.ecosystem.core.utils.utility.eventbus.AppEvent
import com.agree.ecosystem.core.utils.utility.navigation.navHost
import com.agree.ecosystem.core.utils.utility.offline.ConnectivityObserver
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.ActivityDetailSubvasselBinding
import com.agree.ecosystem.monitoring.presentation.banner.offline.OfflineBannerFragment
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselViewModel
import com.agree.locales.presentation.delegation.LocaleActivityDelegate
import com.agree.locales.presentation.delegation.LocaleActivityDelegation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailSubVesselActivity :
    AgrActivity<ActivityDetailSubvasselBinding>(),
    LocaleActivityDelegate by LocaleActivityDelegation(),
    DetailSubVesselContract {

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

        val nav = navHost(R.id.nav_host_detail_subvessel)
        nav.navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.transactionDetailFragment) {
                changeStatusBarColor(StatusBarColors.PRIMARY500)
            } else {
                changeStatusBarColor(StatusBarColors.WHITE)
            }
        }

        lifecycleScope.launch {
            delay(500)
            sharedVm.tabLayout.collectLatest { tab ->
                val fragmentOffline =
                    binding.fcvOfflineBanner.getFragment<OfflineBannerFragment>()
                val fragmentRoot = fragmentOffline.binding.root
                if (tab.text == getString(R.string.label_activities)) {
                    val color = if (connection.isConnected)
                        StatusBarColors.PRIMARY
                    else StatusBarColors.CUSTOMINFO700
                    changeStatusBarColor(color)
                    fragmentRoot.isExpanded = true
                } else {
                    changeStatusBarColor(StatusBarColors.WHITE)
                    fragmentRoot.isExpanded = false
                }
            }
        }
    }

    override fun changeStatusBarColor(colors: StatusBarColors) {
        val value = TypedValue()
        theme.resolveAttribute(colors.value, value, true)
        window.statusBarColor = value.data
    }

    private val sharedVm: SubVesselViewModel by viewModel()
    private val connection: ConnectivityObserver by inject()
}
