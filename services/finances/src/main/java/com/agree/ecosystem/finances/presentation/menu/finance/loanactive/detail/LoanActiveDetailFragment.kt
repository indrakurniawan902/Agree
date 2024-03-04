package com.agree.ecosystem.finances.presentation.menu.finance.loanactive.detail

import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.finances.R
import com.agree.ecosystem.finances.databinding.FragmentLoanActiveDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoanActiveDetailFragment : AgrFragment<FragmentLoanActiveDetailBinding>() {

    private val viewModel: LoanActiveDetailViewModel by viewModel()

    override fun initUI() {
        super.initUI()
    }

    override fun initAction() {
        super.initAction()
    }

    override fun initData() {
        super.initData()
    }

    override fun initObserver() {
        super.initObserver()
        with(binding) {
            ci1.apply {
                bindLeft(context.getString(R.string.label_interest), "10 %")
                bindRight(context.getString(R.string.label_amount_filed), "Rp45.000.000")
            }
            ci2.apply {
                bindLeft(context.getString(R.string.label_this_month_installment), message = "ada")
                bindRight(context.getString(R.string.label_mount_approved), "Rp0")
            }

            ci3.apply {
                bindLeft(context.getString(R.string.label_repayment_limit), message = "ada")
                bindRight(context.getString(R.string.label_repayment_amount), message = "ada")
            }
        }
    }
}
