package com.agree.ecosystem.monitoring.presentation.navigation

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.agree.ecosystem.monitoring.presentation.menu.detailarea.MonitoringDetailFragmentDirections
import com.oratakashi.viewbinding.core.tools.startActivity

class MonitoringNavigationImpl(
    private val nav: NavController?,
    private val activity: FragmentActivity?
) : MonitoringNavigation {
    override fun fromDetailAreaToDetailSubArea() {
        runCatching {
            nav?.navigate(MonitoringDetailFragmentDirections.actionMonitoringDetailFragmentToDetailSubAreaFragment())
        }.onFailure {
        }
    }

    override fun fromListSubVesselToCompanies() {
        runCatching {
            val activityToStart =
                "com.agree.ecosystem.partnership.presentation.base.activity.registerpartnership.RegisterPartnershipActivity"
            val goToRegisterPartnershipActivity = Class.forName(activityToStart)
            activity?.startActivity(goToRegisterPartnershipActivity)
        }
    }
}
