package com.agree.ecosystem.partnership.presentation.navigation

import android.app.Activity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.agree.ecosystem.partnership.presentation.base.activity.registerpartnership.RegisterPartnershipActivity
import com.agree.ecosystem.partnership.presentation.base.activity.statusregister.StatusRegisterActivity
import com.oratakashi.viewbinding.core.tools.startActivity

class ListStatusRegisterNavigationImpl(
    private val nav: NavController?,
    private val activity: Activity?,
) : ListStatusRegisterNavigation {
    override fun getNavController(): NavController? {
        return nav
    }

    override fun goToPrevious() {
        nav?.navigateUp()
    }

    override fun fromStatusRegistrationToDetailStatus(id: String) {
        activity?.startActivity(StatusRegisterActivity::class.java) {
            it.putExtra("submissionId", bundleOf("submissionId" to id))
        }
    }

    override fun fromHomeToCompanies() {
        runCatching {
            activity?.startActivity(RegisterPartnershipActivity::class.java)
        }
    }

    override fun fromDetailInboxToDetailStatus(id: String) {
        activity?.startActivity(StatusRegisterActivity::class.java) {
            it.putExtra("submissionId", bundleOf("submissionId" to "abbe6658-fc51-4acf-b134-695f00646adc"))
        }
    }
}
