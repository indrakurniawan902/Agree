package com.agree.ecosystem.finances.presentation.navigation

import android.app.Activity
import androidx.navigation.NavController
import com.agree.ecosystem.finances.domain.reqres.model.params.ProfileCultivatorParams
import com.agree.ecosystem.finances.presentation.base.activity.finance.FinanceActivity
import com.agree.ecosystem.finances.presentation.base.activity.finance.LoanSubmissionActivity
import com.agree.ecosystem.finances.presentation.base.activity.profile.CultivatorsDataProfilActivity
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.LoanSubmissionCultivatorFragmentDirections
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.LoanSubmissionLandFragmentDirections
import com.agree.ecosystem.finances.utils.FinanceNameOfBundles
import com.agree.ecosystem.utilities.presentation.base.activity.UtilitiesActivity
import com.agree.ecosystem.utilities.presentation.navigation.NavigationScreen
import com.oratakashi.viewbinding.core.tools.startActivity

class LoanSubmissionImpl(private val nav: NavController?, private val activity: Activity) :
    LoanSubmissionNavigation {
    override fun getStartActivity() {
        runCatching {
            activity.startActivity(LoanSubmissionActivity::class.java)
        }
    }

    override fun getNavController(): NavController? {
        return nav
    }

    override fun toTnc() {
        runCatching {
            activity.startActivity(UtilitiesActivity::class.java) {
                it.putExtra("screen", NavigationScreen.Tnc.name)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun navigateUp() {
        nav?.navigateUp()
    }

    override fun toLandSubmissionPage() {
        runCatching {
            nav?.navigate(
                LoanSubmissionCultivatorFragmentDirections.actionLoanSubmissionCultivatorFragmentToLoanSubmissionLandFragment()
            )
        }.onFailure {

        }
    }

    override fun toNominalSubmissionPage() {
        runCatching {
            nav?.navigate(
                LoanSubmissionLandFragmentDirections.actionLoanSubmissionLandFragmentToLoanSubmissionNominalFragment()
            )
        }.onFailure {

        }
    }

    override fun toCollateralPage() {

    }

    override fun submissionSuccessNavigation() {
        runCatching {
            activity.startActivity(FinanceActivity::class.java)
        }
    }

    override fun toProfileCultivator(params: ProfileCultivatorParams) {
        runCatching {
            activity.startActivity(CultivatorsDataProfilActivity::class.java) {
                it.putExtra(
                    FinanceNameOfBundles.PARAM_TO_PROFILE_ACTIVITY.param,
                    params
                )
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun toDetailBudgetPlant(item: String) {
        runCatching {
            nav?.navigate(
                LoanSubmissionLandFragmentDirections.actionLoanSubmissionLandFragmentToBudgetPlanFragment(item)
            )
        }.onFailure {  }
    }
}