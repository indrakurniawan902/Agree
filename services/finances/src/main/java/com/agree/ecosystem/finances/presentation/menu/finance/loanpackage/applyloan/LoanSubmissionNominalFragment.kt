package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.domain.reqres.model.DataSpannable
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.core.utils.utility.singleLinkSpan
import com.agree.ecosystem.finances.R
import com.agree.ecosystem.finances.data.reqres.model.LoanItemList
import com.agree.ecosystem.finances.databinding.FragmentLoanSubmissionNominalBinding
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.GoodsCultivator
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.LoanSubmissionBaseItem
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.NominalCultivator
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.bottomsheet.NominalBottomSheetVerifyFragment
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.nominal.LoanSubmissionNominalAdapter
import com.agree.ecosystem.finances.presentation.navigation.LoanSubmissionNavigation
import com.agree.ecosystem.finances.utils.formatCurrency
import com.agree.ecosystem.finances.utils.parseCurrencyToLong
import com.agree.ui.snackbar.warningSnackBar
import com.oratakashi.viewbinding.core.tools.gone
import com.oratakashi.viewbinding.core.tools.visible
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoanSubmissionNominalFragment : AgrFragment<FragmentLoanSubmissionNominalBinding>() {
    private val loanSubmissionNav: LoanSubmissionNavigation by navigation { activity }
    private val viewModel: LoanSubmissionViewModel by sharedViewModel()

    //    private var totalNominalTemp = mutableMapOf<Int, Long>()
    private var mitraId: String? = ""
    private var companyId: String? = ""


    private val adapter: LoanSubmissionNominalAdapter by lazy {
        LoanSubmissionNominalAdapter({ item, position ->
            if (item is NominalCultivator) {
                viewModel.setNominal(position, item.nominal)
                viewModel.setTotalNominal(position, item.nominal.parseCurrencyToLong())

                with(binding) {
                    tvValueTotal.text = bindTotal()
                }
            } else if (item is GoodsCultivator) {
                viewModel.setNominal(position, item.nominal)
                viewModel.setTotalNominal(position, item.nominal.parseCurrencyToLong())
                viewModel.setLoanItemList(
                    position,
                    LoanItemList(
                        item.data.itemName,
                        item.data.itemPrice,
                        item.data.itemQuantity,
                        item.data.itemUnit,
                        item.data.itemMaxUnit,
                        item.loanItemRequest.toInt()
                    )
                )
                binding.tvValueTotal.text = bindTotal()
            }
        }, viewModel)
    }

    private fun isValidated(): Boolean {
        val datas = mutableMapOf<String, Boolean>()
        var isValid: Boolean
        adapter.listData.forEach {
            it?.let {
                if (it is NominalCultivator) {
                    when {
                        it.nominal.isEmpty() -> datas.set(it.data.name, false)
                        it.nominal.parseCurrencyToLong() > it.data.loanPackageMaxAmount -> datas.set(
                            it.data.name,
                            false
                        )

                        it.nominal.parseCurrencyToLong() < it.data.loanPackageMinAmount -> datas.set(
                            it.data.name,
                            false
                        )

                        else -> datas.set(it.data.name, true)
                    }
                } else if (it is GoodsCultivator) {
                    isValid = viewModel.getSumTotalNominal().toInt() > 0
                }
            }
        }
        isValid = datas.all {
            it.value == true
        }
        return isValid
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.navController = loanSubmissionNav.getNavController()
            toolbar.setBackButtonOnClick { loanSubmissionNav.navigateUp() }
        }
    }

    private fun createCustomSpan(firstChar: Int, lastChar: Int, action: () -> Unit): DataSpannable {
        return DataSpannable(listOf(
            DataSpannable.ListSpinnable(firstChar, lastChar) {
                action()
            }
        ))
    }

    override fun initUI() {
        super.initUI()
        mitraId = requireActivity().intent.getStringExtra("mitraId")
        companyId = requireActivity().intent.getStringExtra("companyId")
        with(binding) {

//            tvTncLoan.text =
//                getString(R.string.label_tnc_loan)
//                    .customLinkSpan(
//                    requireContext(), createCustomSpan(firstChar = 41, lastChar = 59) {
//                        loanSubmissionNav.toTnc()
//                        cbTnc.cancelPendingInputEvents()
//                    }, isUnderline = true
//                )
            tvTncLoan.text = getString(R.string.label_tnc_loan).singleLinkSpan(requireContext()) {
                loanSubmissionNav.toTnc()
            }
            tvTncLoan.movementMethod = LinkMovementMethod.getInstance()
            tvValueTotal.text = bindTotal()
        }
    }


    private fun bindTotal() =
        getString(
            R.string.label_loan_nominal_total,
            formatCurrency(viewModel.getSumTotalNominal())
        )

    override fun initObserver() {
        super.initObserver()
        with(binding) {
            stepperLoan.setStepper(
                viewModel.stepperList.value.map {
                    it.value
                }, viewModel.stepperList.value.size - 1
            )
        }
        val data = ArrayList<LoanSubmissionBaseItem>()
        val viewModelData = viewModel.selectedCultivator.value

        viewModel.setMitraAndCompanyId(companyId, mitraId)
        viewModelData?.forEach { farmer ->
            val selected = farmer.plantingSeasonData.map {
                viewModel.selectedLand.value?.filter { checkableLand -> checkableLand.farmerId == farmer.farmerId }
                NominalCultivator(farmer, "", 0.0, 0.0)
            }
            data.addAll(selected)
        }
        adapter.apply {
            adapter.clear()
            adapter.addOrUpdateAll(data)
        }
        binding.rvLoanSubmissionNominal.adapter = adapter
        binding.tvLabelTotalWithPackage.text = viewModel.detailLoanPackage.loanPackageName
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            cbTnc.addOnCheckedStateChangedListener { checkBox, state ->
                viewModel.setTnc(checkBox.isChecked)
            }
            btnSelect.setOnClickListener {
                with(viewModel) {
                    if (isValidated() && bnTncChecked.value) {
                        NominalBottomSheetVerifyFragment().show(
                            childFragmentManager,
                            NominalBottomSheetVerifyFragment.TAG
                        )
                    } else {
                        warningSnackBar(getString(com.agree.ecosystem.finances.R.string.label_please_complete_the_datas))
                        tvTncLoanError.visible()
                    }
                    if (!bnTncChecked.value) tvTncLoanError.visible() else tvTncLoanError.gone()
                }
            }
        }
    }

    /* migration to viewmodel
    private fun submitLoan() {
        val selectedCultivator = viewModel.selectedCultivator.value
        val loanRequestedData = selectedCultivator?.mapIndexed { index, applyLoanCultivator ->
            LoanRequestedData(
                loanRequestedAmount = viewModel.totalNominal.value?.get(index)?.toDouble()
                    ?: 0.toDouble(),
                loanItemList = loanItemListTemp.values.toList(),
                loanMitraId = "",
                loanPlantingSeasonIds = viewModel.selectedLand.value?.filter { it.farmerId == applyLoanCultivator.farmerId }
                    ?.map { it.data.plantingSeasonId } ?: listOf(),
                loanCollateralId = viewModel.selectedCollateral.value?.find { collateral -> collateral.farmerId == applyLoanCultivator.farmerId }?.data?.collateralId,
                loanBorrowerId = applyLoanCultivator.borrowerId,
                loanFarmerId = applyLoanCultivator.farmerId,
                loanCompanyId = ""
            )
        }
        viewModel.applyLoan("", loanRequestedData)
    }

     */

}