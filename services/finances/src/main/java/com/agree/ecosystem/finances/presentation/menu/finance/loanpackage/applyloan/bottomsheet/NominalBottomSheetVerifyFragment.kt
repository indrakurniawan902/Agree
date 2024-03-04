package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.agree.ecosystem.core.utils.base.AgrBottomSheet
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.finances.databinding.FragmentNominalBottomSheetVerifyBinding
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.LoanSubmissionViewModel
import com.agree.ecosystem.finances.presentation.navigation.LoanSubmissionNavigation
import com.agree.ecosystem.finances.utils.formatCurrency
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class NominalBottomSheetVerifyFragment() : AgrBottomSheet() {

    private val binding: FragmentNominalBottomSheetVerifyBinding by viewBinding()
    private val viewModel: LoanSubmissionViewModel by sharedViewModel()
    private val prefs: AgrPrefUsecase by inject()

    override fun initAction() {
        super.initAction()
        with(binding) {
            ivClose.setOnClickListener { dismissNow() }
            tvLabelTotalWithPackage.text = viewModel.detailLoanPackage.loanPackageName
            tvValueTotal.text = getString(
                com.agree.ecosystem.finances.R.string.label_loan_nominal_total,
                formatCurrency(viewModel.getSumTotalNominal())
            )

        }
    }

    override fun initObserver() {
        super.initObserver()
        binding.btnSubmit.setOnClickListener {
            dismissNow()
            viewModel.submitLoan(prefs.userId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = binding.root

    companion object {
        const val TAG = "LoanSubmissionVerifyBottomSheet"
    }

    private val nav: LoanSubmissionNavigation by navigation { activity }

}