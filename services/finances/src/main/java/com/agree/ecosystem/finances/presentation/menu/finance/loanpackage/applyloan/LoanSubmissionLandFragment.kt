package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan

import androidx.navigation.fragment.navArgs
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.finances.R
import com.agree.ecosystem.finances.databinding.FragmentLoanSubmissionLandBinding
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.CheckableLand
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.DropdownLand
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.LoanSubmissionBaseItem
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.land.LoanSubmissionLandAdapter
import com.agree.ecosystem.finances.presentation.navigation.LoanSubmissionNavigation
import com.agree.ecosystem.finances.utils.toCheckableLand
import com.agree.ui.snackbar.warningSnackBar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoanSubmissionLandFragment : AgrFragment<FragmentLoanSubmissionLandBinding>() {

    private val loanSubmissionNav: LoanSubmissionNavigation by navigation { activity }
    private val viewModel: LoanSubmissionViewModel by sharedViewModel()
    private val landList = ArrayList<CheckableLand>()
    private val args: LoanSubmissionLandFragmentArgs by navArgs()

    private val adapter: LoanSubmissionLandAdapter by lazy {
        LoanSubmissionLandAdapter({ item, value, position ->
            updateLand(item)
        }, { item ->
            budgetPlant(item)
        }, viewModel)
    }

    private fun cleanData() {
        viewModel.selectedLand.value?.clear()
        landList.clear()
        viewModel.resetNominalandTotalNominal()
    }

    override fun onDestroy() {
        super.onDestroy()
        cleanData()
    }

    override fun initUI() {
        super.initUI()
    }


    override fun initAction() {
        super.initAction()
        with(binding) {
            tvApplyLoanLabel.text = getString(R.string.label_loan_submission_land)
            toolbar.navController = loanSubmissionNav.getNavController()
            toolbar.setBackButtonOnClick { loanSubmissionNav.navigateUp() }

            btnSelect.setOnClickListener {
                if (viewModel.isSelectedLandValid()) {
                    toNominal()
                } else {
                    warningSnackBar(getString(R.string.label_please_choose_your_land))
                }
            }
        }
    }

    override fun initObserver() {
        super.initObserver()
        val data = ArrayList<LoanSubmissionBaseItem>()
        val viewModelData = viewModel.selectedCultivator.value

        with(binding) {
            stepperLoan.setStepper(
                viewModel.stepperList.value.map { it.value }, if (args.isFromCollateral) 2 else 1
            )
        }

        viewModelData?.forEach { farmer ->
            val land = farmer.plantingSeasonData.map { plantingSeason ->
                CheckableLand(
                    farmerId = farmer.farmerId,
                    data = plantingSeason
                )
            }
            DropdownLand(farmer, land).let(data::add)
            data.addAll(land)
            landList.addAll(land)
        }
        adapter.apply {
            adapter.clear()
            adapter.addOrUpdateAll(data)
        }

        binding.rvLoanSubmission.adapter = adapter

    }

    private fun toNominal() {
        loanSubmissionNav.toNominalSubmissionPage()
    }

    private fun updateLand(item: LoanSubmissionBaseItem) {
        val land = item.toCheckableLand()
        viewModel.updateSelectedLand(land)
    }

    private fun budgetPlant(item: LoanSubmissionBaseItem) {
        if (item is CheckableLand) {
            loanSubmissionNav.toDetailBudgetPlant(item.data.budgetPlanId)
        }
    }
}
