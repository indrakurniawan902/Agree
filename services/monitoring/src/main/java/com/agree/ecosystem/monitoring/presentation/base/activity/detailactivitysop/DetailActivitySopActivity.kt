package com.agree.ecosystem.monitoring.presentation.base.activity.detailactivitysop

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.core.utils.base.AgrActivity
import com.agree.ecosystem.core.utils.utility.eventbus.AppEvent
import com.agree.ecosystem.core.utils.utility.extension.initGraph
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.ActivityDetailActivitySopBinding
import com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop.individual.IndividualMonitoringFragmentArgs
import com.agree.ecosystem.monitoring.presentation.navigation.params.DetailActivityParams
import com.agree.ecosystem.monitoring.utils.MonitoringRecordType
import com.agree.ecosystem.monitoring.utils.MonitoringType
import com.agree.locales.presentation.delegation.LocaleActivityDelegate
import com.agree.locales.presentation.delegation.LocaleActivityDelegation
import kotlinx.coroutines.flow.collectLatest

class DetailActivitySopActivity :
    AgrActivity<ActivityDetailActivitySopBinding>(),
    LocaleActivityDelegate by LocaleActivityDelegation() {

    init {
        initLocale(this, super.getDelegate())
    }

    override fun getDelegate(): AppCompatDelegate {
        return getLocaleDelegate()
    }

    override fun initUI() {
        super.initUI()
        val payload = intent.getParcelableExtra<DetailActivityParams>("data")
        val subVesselId = intent.getStringExtra("subVesselId")
        val isCompleted = payload?.isCompleted ?: false
        val type = payload?.type
        val recordType = payload?.recordType

        if ((recordType == MonitoringRecordType.INDIVIDUAL.value && type == MonitoringType.ENTRY_POINT.value && isCompleted) ||
            (recordType == MonitoringRecordType.INDIVIDUAL.value && type != MonitoringType.ENTRY_POINT.value)
        ) {
            initGraph(
                R.id.nav_host_detail_activity_sop,
                R.navigation.nav_detail_activity_sop,
                IndividualMonitoringFragmentArgs(
                    subVesselId = subVesselId.toString(),
                    type = type.toString(),
                    activityName = payload?.activityName.toString(),
                    guideContent = payload?.guideContent.toString(),
                    date = payload?.date.toString()
                ).toBundle()
            ) {
                it.setStartDestination(R.id.individualMonitoringFragment)
            }
        }
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
