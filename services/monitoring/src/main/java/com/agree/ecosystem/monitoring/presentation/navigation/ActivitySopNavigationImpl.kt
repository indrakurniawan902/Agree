package com.agree.ecosystem.monitoring.presentation.navigation

import android.app.Activity
import androidx.navigation.NavController
import com.agree.ecosystem.core.utils.utility.navigation.getDefaultNavOptions
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.domain.reqres.model.insertactivitysop.NavToAdditionalActivitySopParams
import com.agree.ecosystem.monitoring.presentation.base.activity.detailactivitysop.DetailActivitySopActivity
import com.agree.ecosystem.monitoring.presentation.base.activity.insertadditionalactivitysop.InsertAdditionalActivitySopActivity
import com.agree.ecosystem.monitoring.presentation.menu.cultivationactivities.CultivationActivitiesFragmentDirections
import com.agree.ecosystem.monitoring.presentation.navigation.params.DetailActivityParams
import com.agree.ecosystem.monitoring.utils.Constant
import com.oratakashi.viewbinding.core.tools.startActivity

class ActivitySopNavigationImpl(
    private val nav: NavController?,
    private val activity: Activity?
) : ActivitySopNavigation {

    override fun fromActivityInfoToHistory() {
        runCatching {
            nav?.navigate(
                CultivationActivitiesFragmentDirections.actionActivitySopFragmentToHistoryActivityFragment()
            )
        }.onFailure {
            it.printStackTrace()
            nav?.navigate(R.id.historyActivityFragment, null, getDefaultNavOptions())
        }
    }

    override fun fromActivityInfoToDetailActivity(subVesselId: String, navParams: DetailActivityParams) {
        runCatching {
            activity?.startActivity(DetailActivitySopActivity::class.java) {
                it.putExtra("subVesselId", subVesselId)
                it.putExtra("data", navParams)
            }
        }
    }

    override fun fromActivityInfoToInsertAdditionalActivity(navParams: NavToAdditionalActivitySopParams) {
        activity?.startActivity(InsertAdditionalActivitySopActivity::class.java) {
            it.putExtra(Constant.NAV_ADDITIONAL_ACTIVITY_PARAMS_BUNDLE, navParams)
        }
    }

    override fun goToPrevious() {
        nav?.navigateUp()
    }
}
