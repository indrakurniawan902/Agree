package com.agree.ecosystem.finances.presentation.base.activity.finance

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.core.utils.base.AgrActivity
import com.agree.ecosystem.core.utils.domain.reqres.model.FinanceCustomMapData
import com.agree.ecosystem.core.utils.utility.eventbus.AppEvent
import com.agree.ecosystem.core.utils.utility.extension.initGraph
import com.agree.ecosystem.finances.R
import com.agree.ecosystem.finances.databinding.ActivityLoanSubmissionBinding
import com.agree.ecosystem.finances.domain.reqres.model.DetailLoanPackage
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.LoanSubmissionCultivatorFragmentArgs
import com.agree.locales.presentation.delegation.LocaleActivityDelegate
import com.agree.locales.presentation.delegation.LocaleActivityDelegation
import kotlinx.coroutines.flow.collectLatest

class LoanSubmissionActivity : AgrActivity<ActivityLoanSubmissionBinding>(),
    LocaleActivityDelegate by LocaleActivityDelegation() {

    init {
        initLocale(this, super.getDelegate())
    }

    override fun getDelegate(): AppCompatDelegate {
        return getLocaleDelegate()
    }

    override fun initUI() {
        super.initUI()

        val loanPackageType = intent.getStringExtra("loanPackageType")
        val detailLoanPackage = intent.getParcelableExtra<DetailLoanPackage>("loanPackage")
        val requiredField =
            intent.getParcelableArrayListExtra<FinanceCustomMapData>("requiredField")
        val from = intent.getStringExtra("from")

        lifecycleScope.launchWhenResumed {
            eventBus.events.collectLatest {
                when (it) {
                    AppEvent.FORCE_LOGOUT -> finish()
                    else -> return@collectLatest
                }
            }
        }

        initGraph(
            R.id.nav_submission_loan,
            R.navigation.nav_submission_loan,
            LoanSubmissionCultivatorFragmentArgs(
                detailLoanPackage = detailLoanPackage,
                loanPackageType = loanPackageType ?: "",
                from = from ?: "",
                requiredField = requiredField?.toTypedArray()
            ).toBundle()
        ) {
            it.setStartDestination(R.id.loanSubmissionCultivatorFragment)
        }
    }
}
