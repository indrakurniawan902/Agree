package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.agree.ecosystem.core.utils.base.AgrBottomSheet
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.finances.R
import com.agree.ecosystem.finances.databinding.FragmentLackBottomSheetBinding
import com.agree.ecosystem.finances.domain.reqres.model.DetailLoanPackage
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.ApplyLoanCultivator
import com.agree.ecosystem.finances.domain.reqres.model.params.ProfileCultivatorParams
import com.agree.ecosystem.finances.presentation.navigation.LoanSubmissionNavigation
import com.agree.ecosystem.finances.utils.LoanEligibilityError
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding

class LackBottomSheetFragment(
    val eligibleErrorMessage: List<String>? = listOf(),
    val isDataCultivatorIssue: Boolean = false,
    val applyLoanCultivator: ApplyLoanCultivator? = null,
    val loanDetailPackage: DetailLoanPackage? = null,
    val requiredFields: List<String>? = listOf()
) : AgrBottomSheet() {

    private val binding: FragmentLackBottomSheetBinding by viewBinding()

    override fun initAction() {
        super.initAction()
        with(binding) {
            ivClose.setOnClickListener { dismiss() }
            containerMaximumLoanActive.bindData(
                getString(R.string.label_message_in_active_loan),
                getString(R.string.label_desc_in_active_loan),
                showIconRight = false,
                statusComplete = if (eligibleErrorMessage?.contains(
                        LoanEligibilityError.HAS_ACTIVE_LOAN.value
                    ) == true
                ) true else false
            )

            containerProfileData.bindData(
                getString(R.string.label_title_incomplete_profile_data),
                getString(R.string.label_desc_incomplete_profile_data),
                showIconRight = true,
                statusComplete = if (eligibleErrorMessage?.contains(
                        LoanEligibilityError.INCOMPLETE_DATA.value
                    ) == true
                ) true else false
            )

            containerActiveArea.bindData(
                getString(R.string.label_message_no_land_active),
                getString(R.string.label_desc_no_land_active),
                showIconRight = false,
                statusComplete = if (eligibleErrorMessage?.contains(
                        LoanEligibilityError.NO_ACTIVE_LAND.value
                    ) == true
                ) true else false
            )

            containerMaximumLoanActive.setOnClickListener { dismissNow() }

            containerProfileData.setOnClickListener {
                nav.toProfileCultivator(
                    ProfileCultivatorParams(
                        cultivator = null,
                        applyLoanCultivator = applyLoanCultivator,
                        detailLoanPackage = loanDetailPackage,
                        isFromLoan = true,
                        requiredFields = requiredFields
                    )
                )
                dismissNow()
            }

            containerActiveArea.setOnClickListener {
                dismissNow()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    companion object {
        const val TAG = "LackBottomSheet"
    }

    private val nav: LoanSubmissionNavigation by navigation { activity }
}
