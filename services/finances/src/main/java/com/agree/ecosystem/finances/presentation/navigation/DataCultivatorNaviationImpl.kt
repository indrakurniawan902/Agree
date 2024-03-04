package com.agree.ecosystem.finances.presentation.navigation

import android.app.Activity
import androidx.navigation.NavController
import com.agree.ecosystem.finances.presentation.base.activity.profile.CultivatorsDataProfilActivity
import com.oratakashi.viewbinding.core.tools.startActivity

class DataCultivatorNaviationImpl(
    private val nav: NavController?,
    private val activity: Activity?
) : DataCultivatorNavigation {
    override fun backToPreviousActivity() {
        activity?.finish()
    }

    override fun getNavController(): NavController? {
        return nav
    }

    override fun fromTabCultivatorDataToCultivatorDataActivity(mitraId: String) {
        runCatching {
            activity?.startActivity(CultivatorsDataProfilActivity::class.java)
            {
                it.putExtra("mitraId", mitraId)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }
}
