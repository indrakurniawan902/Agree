package com.agree.ecosystem.monitoring.presentation.navigation

import android.app.Activity
import androidx.navigation.NavController
import com.agree.ecosystem.monitoring.domain.reqres.model.incident.Incident
import com.agree.ecosystem.monitoring.domain.reqres.model.insertactivitysop.NavToAdditionalActivitySopParams
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVessel
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.Transaction
import com.agree.ecosystem.monitoring.presentation.base.activity.activitysop.ActivitySopActivity
import com.agree.ecosystem.monitoring.presentation.base.activity.detailactivitysop.DetailActivitySopActivity
import com.agree.ecosystem.monitoring.presentation.base.activity.insertadditionalactivitysop.InsertAdditionalActivitySopActivity
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.DetailSubVesselFragmentDirections
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident.report.IncidentReportFragmentDirections
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.transaction.detail.TransactionDetailFragmentDirections
import com.agree.ecosystem.monitoring.presentation.navigation.params.DetailActivityParams
import com.agree.ecosystem.monitoring.utils.Constant
import com.agree.ecosystem.smartfarming.domain.reqres.model.detailsubvessel.DetailSubVesselParams
import com.agree.ecosystem.smartfarming.presentation.base.activity.SmartFarmingActivity
import com.oratakashi.viewbinding.core.tools.startActivity

class DetailSubVesselNavigationImpl(
    private val nav: NavController?,
    private val activity: Activity?
) : DetailSubVesselNavigation {
    override fun getNavController(): NavController? {
        return nav
    }

    override fun goToPrevious() {
        nav?.navigateUp()
    }

    override fun fromDetailSubVesselToActivitySop(subVesselId: String, detailSubVessel: DetailSubVessel) {
        runCatching {
            activity?.startActivity(ActivitySopActivity::class.java) {
                it.putExtra("data", detailSubVessel)
                it.putExtra("subVesselId", subVesselId)
            }
        }
    }

    override fun fromDetailSubVesselToDetailActivity(subVesselId: String, navParams: DetailActivityParams) {
        runCatching {
            activity?.startActivity(DetailActivitySopActivity::class.java) {
                it.putExtra("subVesselId", subVesselId)
                it.putExtra("data", navParams)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromDetailSubVesselToAddIncident() {
        nav?.navigate(
            DetailSubVesselFragmentDirections.actionDetailSubVesselFragmentToAddIncidentFragment()
        )
    }

    override fun fromDetailSubVesselToInsertAdditionalActivity(navParams: NavToAdditionalActivitySopParams) {
        activity?.startActivity(InsertAdditionalActivitySopActivity::class.java) {
            it.putExtra(Constant.NAV_ADDITIONAL_ACTIVITY_PARAMS_BUNDLE, navParams)
        }
    }

    override fun fromDetailSubVesselToIncidentReport(incident: Incident?) {
        runCatching {
            nav?.navigate(DetailSubVesselFragmentDirections.actionDetailSubVesselFragmentToIncidentReportFragment(incident))
        }
    }

    override fun fromIncidentReportToIncidentDetail(incident: Incident?) {
        runCatching {
            nav?.navigate(IncidentReportFragmentDirections.actionIncidentReportFragmentToIncidentDetailFragment(incident))
        }
    }

    override fun fromDetailSubVesselToInsertTransaction(transactionId: String?) {
        runCatching {
            nav?.navigate(DetailSubVesselFragmentDirections.actionDetailSubVesselFragmentToInsertUpdateTransactionFragment(transactionId))
        }
    }

    override fun fromTransactionDetailToUpdateTransaction(transactionId: String?) {
        runCatching {
            nav?.navigate(TransactionDetailFragmentDirections.actionTransactionDetailFragmentToInsertUpdateTransactionFragment(transactionId))
        }
    }

    override fun fromDetailSubVesselToTransactionDetail(transaction: Transaction?) {
        runCatching {
            nav?.navigate(DetailSubVesselFragmentDirections.actionDetailSubVesselFragmentToTransactionDetail(transaction))
        }
    }

    override fun fromDetailSubVesselToSmartFarming(detailSubVessel: DetailSubVessel) {
        activity?.startActivity(SmartFarmingActivity::class.java) {
            it.putExtra(
                "data",
                DetailSubVesselParams(
                    detailSubVessel.id,
                    detailSubVessel.subSectorId,
                    detailSubVessel.subVesselName,
                    detailSubVessel.workerName
                )
            )
        }
    }
}
