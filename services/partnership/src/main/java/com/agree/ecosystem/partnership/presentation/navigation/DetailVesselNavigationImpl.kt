package com.agree.ecosystem.partnership.presentation.navigation

import android.app.Activity
import androidx.navigation.NavController
import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import com.agree.ecosystem.monitoring.presentation.base.activity.detailsubvassel.DetailSubVesselActivity
import com.oratakashi.viewbinding.core.tools.startActivity

class DetailVesselNavigationImpl(
    private val nav: NavController?,
    private val activity: Activity?
) : DetailVesselNavigationUsecase {
    override fun getNavController(): NavController? {
        return nav
    }

    override fun goToPrevious() {
        nav?.navigateUp()
    }

    override fun fromDetailVesselToDetailSubVessel(subVessel: SubVessel) {
        runCatching {
            activity?.startActivity(DetailSubVesselActivity::class.java) {
                it.putExtra("subVessel", subVessel)
            }
        }
    }
}
