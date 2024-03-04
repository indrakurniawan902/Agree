package com.agree.ecosystem.monitoring.presentation.navigation

import android.app.Activity
import androidx.navigation.NavController
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesselIndividual.IndividualSubVessel
import com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop.DetailActivitySopFragmentDirections
import com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop.individual.IndividualMonitoringFragmentDirections
import com.agree.ui.data.reqres.model.FormItem

class DetailActivitySopNavigationImpl(
    private val nav: NavController?,
    private val activity: Activity?
) : DetailActivitySopNavigation {
    override fun getNavController(): NavController? {
        return nav
    }

    override fun goToPrevious() {
        nav?.navigateUp()
    }

//    override fun fromDetailActivitySopToIndividualMonitoring(
//        subVesselId: String,
//        activityName: String,
//        guideContent: String,
//        date: String
//    ) {
//        runCatching {
//            nav?.navigate(
//                DetailActivitySopFragmentDirections.actionDetailActivitySopToIndividualMonitoringFragment(
//                    subVesselId,
//                    activityName,
//                    guideContent,
//                    date
//                )
//            )
//        }.onFailure {
//        }
//    }

    override fun fromDetailActivitySopToIndividualMonitoring(
        subVesselId: String,
        type: String,
        activityName: String,
        guideContent: String,
        date: String
    ) {
        runCatching {
            nav?.navigate(
                DetailActivitySopFragmentDirections.actionDetailActivitySopToIndividualMonitoringFragment(
                    subVesselId,
                    type,
                    activityName,
                    guideContent,
                    date
                )
            )
        }.onFailure {
        }
    }

    override fun fromIndividualMonitoringToDetailActivitySop(individualSubVessel: IndividualSubVessel?) {
        runCatching {
            nav?.navigate(
                IndividualMonitoringFragmentDirections.actionIndividualMonitoringFragmentToDetailActivitySop(individualSubVessel)
            )
        }.onFailure {
        }
    }

    override fun fromDetailActivitySopToActivitySummary(
        activityName: String,
        formItems: List<FormItem>
    ) {
        runCatching {
            nav?.navigate(
                DetailActivitySopFragmentDirections.actionDetailActivitySopToActivitySummaryFragment(activityName, formItems.toTypedArray())
            )
        }
    }
}
