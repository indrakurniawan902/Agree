package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.budgetplan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.finances.R
import com.agree.ecosystem.finances.databinding.FragmentBudgetPlanBinding
import com.agree.ecosystem.finances.presentation.navigation.LoanSubmissionNavigation

class BudgetPlanFragment : AgrFragment<FragmentBudgetPlanBinding>() {

    private val loanSubmissionNav: LoanSubmissionNavigation by navigation { activity }

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.navController = loanSubmissionNav.getNavController()
        }
    }

    override fun initData() {
        super.initData()
    }

    override fun initObserver() {
        super.initObserver()
    }

    override fun initUI() {
        super.initUI()
    }
}