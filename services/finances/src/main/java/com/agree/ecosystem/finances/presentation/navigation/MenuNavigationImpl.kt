package com.agree.ecosystem.finances.presentation.navigation

import android.app.Activity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.agree.ecosystem.core.utils.domain.reqres.model.FinanceCustomMapData
import com.agree.ecosystem.finances.domain.reqres.model.DetailLoanPackage
import com.agree.ecosystem.finances.presentation.base.activity.finance.LoanSubmissionActivity
import com.agree.ecosystem.finances.presentation.menu.finance.FinanceFragmentDirections
import com.agree.ecosystem.partnership.presentation.base.activity.registerpartnership.RegisterPartnershipActivity
import com.oratakashi.viewbinding.core.tools.startActivity

class MenuNavigationImpl(private val nav: NavController?, private val activity: Activity?) :
    MenuNavigation {
    override fun toLoanSubmissionActivity(
        loanPackage: DetailLoanPackage,
        loanPackageType: String?,
        requiredField: ArrayList<FinanceCustomMapData>?,
        from: String,
        companyId: String,
        mitraId: String
    ) {
        activity?.startActivity(LoanSubmissionActivity::class.java) {
            it.putParcelableArrayListExtra("requiredField", requiredField)
            it.putExtra("loanPackage", loanPackage)
            it.putExtra("loanPackageType", loanPackageType)
            it.putExtra("from", from)
            it.putExtra("companyId", companyId)
            it.putExtra("mitraId", mitraId)
        }
    }


    override fun getNavController(): NavController? {
        return nav
    }

    override fun fromListPackageToDetailPackage(
        loanPackageId: String,
        companyId: String,
        mitraId: String
    ) {
        kotlin.runCatching {
            nav?.navigate(
                FinanceFragmentDirections.actionFinanceFragmentToDetailLoanPackageFragment2(
                    loanPackageId,
                    companyId,
                    mitraId
                )
            )
        }.onFailure {

        }
    }

    override fun fromListPackageToCompanies(subSector: Array<SubSector>) {
        runCatching {
            activity?.startActivity(RegisterPartnershipActivity::class.java) {
                it.putExtra("selectedSector", bundleOf("selectedSector" to subSector))
            }
        }
    }
}
