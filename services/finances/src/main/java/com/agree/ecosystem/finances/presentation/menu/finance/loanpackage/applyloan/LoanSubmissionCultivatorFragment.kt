package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan

import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.domain.reqres.model.FinanceCustomMapData
import com.agree.ecosystem.core.utils.utility.DialogUtils
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.finances.R
import com.agree.ecosystem.finances.data.source.LoanDetailBorrowerWithLoanPackageIdFakeData
import com.agree.ecosystem.finances.databinding.FragmentLoanSubmissionCultivatorBinding
import com.agree.ecosystem.finances.domain.reqres.model.DetailLoanPackage
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.ApplyLoanCultivator
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.CheckableCultivator
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.LoanSubmissionBaseItem
import com.agree.ecosystem.finances.domain.reqres.model.params.ProfileCultivatorParams
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.bottomsheet.LackBottomSheetFragment
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.cultivator.LoanSubmissionCultivatorAdapter
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.cultivator.LoanSubmissionCultivatorDataContract
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.cultivator.LoanSubmissionCultivatorObserver
import com.agree.ecosystem.finances.presentation.navigation.LoanSubmissionNavigation
import com.agree.ecosystem.finances.utils.toCheckableCultivator
import com.agree.ui.snackbar.warningSnackBar
import com.oratakashi.viewbinding.core.tools.finish
import com.oratakashi.viewbinding.core.tools.isNotNull
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoanSubmissionCultivatorFragment : AgrFragment<FragmentLoanSubmissionCultivatorBinding>(),
    LoanSubmissionCultivatorDataContract {

    private var lastAdapterCheckedPosition: CheckableCultivator? = null
    private var loanPackageType: String? = ""
    private var loanDetailPackage: DetailLoanPackage? = null
    private var requiredField: ArrayList<FinanceCustomMapData>? = arrayListOf()
    private var from: String? = ""
    private var mitraId: String? = ""

    private val loanSubmissionNav: LoanSubmissionNavigation by navigation { activity }
    private val viewModel: LoanSubmissionViewModel by sharedViewModel()
    private val prefs: AgrPrefUsecase by inject()
    private val adapter: LoanSubmissionCultivatorAdapter by lazy {
        LoanSubmissionCultivatorAdapter({ item, isChecked, index ->
            checkForAddCultivator(item, isChecked, index)
        }, { item -> toProfileFragment(item) }, { item ->
            showLack(item)
        }, viewModel
        )
    }

    private fun toProfileFragment(item: LoanSubmissionBaseItem) {
        val data = item.toCheckableCultivator().data
        loanSubmissionNav.toProfileCultivator(
            params = ProfileCultivatorParams(
                cultivator = null,
                applyLoanCultivator = data,
                detailLoanPackage = null,
                isFromLoan = true,
                requiredFields = null
            )
        )
    }

    override fun initUI() {
        super.initUI()
        getBundle()
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.navController = loanSubmissionNav.getNavController()
            tvApplyLoanLabel.text = getString(R.string.label_loan_submission_cultivator)

            btnSelect.setOnClickListener {
                checkForNextStep(viewModel.selectedCultivator.value?.size ?: 0)
            }

            toolbar.setBackButtonOnClick { finish() }
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(LoanSubmissionCultivatorObserver(this, viewModel))
        preBeforeMainApiHit()

        fetchCheckMemberEligibility(
            mitraId ?: "", loanDetailPackage?.loanPackageId ?: ""
        )
    }

    override fun checkMemberEligibilityOnDefault() {}

    override fun fetchCheckMemberEligibility(mitraId: String, loanPackageId: String) {
        viewModel.fetchCheckMemberEligibility(mitraId, loanPackageId)
    }

    override fun checkMemberEligibilityOnLoading() {

    }

    override fun checkMemberEligibilityOnSuccess(data: List<ApplyLoanCultivator>) {
        utilDialogforTest(data)
    }

    private fun utilDialogforTest(data: List<ApplyLoanCultivator>) {
        DialogUtils.showCustomDialog(
            context = requireContext(),
            title = "",
            message = getString(R.string.lable_will_use_dummy_data),
            positiveAction = Pair(getString(R.string.lable_yes)) {
                utilBindingforTest(LoanDetailBorrowerWithLoanPackageIdFakeData().datas)
            },
            negativeAction = Pair(getString(R.string.lable_wont_)) {
                utilBindingforTest(data)
            },
            autoDismiss = true
        )
    }

    private fun utilBindingforTest(data: List<ApplyLoanCultivator>) {
        viewModel.checkStepperList(data)
        binding.stepperLoan.setStepper(viewModel.stepperList.value.let {
            it.let { list ->
                list.map {
                    it.value
                }
            }
        }, 0)

        adapter.apply {
            adapter.clear()
            adapter.addOrUpdateAll(data.map {
                CheckableCultivator(it)
            })
        }
        binding.rvLoanSubmission.adapter = adapter
    }

    override fun checkMemberEligibilityOnEmpty(data: List<ApplyLoanCultivator>) {
    }

    override fun checkMemberEligibilityOnError(e: Throwable?) {
        val fakeData = LoanDetailBorrowerWithLoanPackageIdFakeData().datas
        checkMemberEligibilityOnSuccess(fakeData)
    }

    private fun checkEligibility(data: ApplyLoanCultivator): List<String> {
        val unCompleteData = mutableListOf<String>()
        val requiredPackage = mutableListOf<String>()

        data.isEligibleIncompleteDataGroup?.forEach { text ->
            unCompleteData.add(text)
        }

        requiredField?.forEach {
            requiredPackage.add(it.key)
        }

        return unCompleteData.filter { requiredPackage.distinct().contains(it) }
    }

    private fun checkForAddCultivator(
        item: LoanSubmissionBaseItem, isChecked: Boolean, index: Int
    ) {
        val (isEligible, data) = checkMemberEligibility(item)

        if (!isEligible) {
            item.toCheckableCultivator().isChecked = false
            adapter.updateAt(index, item)
        } else if (loanDetailPackage?.loanPackageItemList.isNullOrEmpty() && lastAdapterCheckedPosition.isNotNull()) {
            lastAdapterCheckedPosition?.let {
                /* belum tau ini buat apa (hasil migrasi)
                val oldPosition =
                    adapter.listData.indexOfFirst { cultivator -> (cultivator as CheckableCultivator).data.farmerId == it.data.farmerId }
                if (it.data.farmerId != data.data.farmerId) {
                    lastAdapterCheckedPosition?.isChecked = false
                    adapter.updateAt(oldPosition, it)
                }
                 */
                viewModel.updateSelectedCultivator(data.data, isChecked, false)
                lastAdapterCheckedPosition = data
            }
        } else {
            viewModel.updateSelectedCultivator(data.data, isChecked, true)
            lastAdapterCheckedPosition = data
        }
    }

    private fun getBundle() {
        loanPackageType = requireActivity().intent.getStringExtra("loanPackageType") ?: ""
        loanDetailPackage =
            requireActivity().intent.getParcelableExtra<DetailLoanPackage>("loanPackage")
        requiredField =
            requireActivity().intent.getParcelableArrayListExtra<FinanceCustomMapData>("requiredField")
        from = requireActivity().intent.getStringExtra("from")
        mitraId = requireActivity().intent.getStringExtra("mitraId")
    }

    private fun checkForNextStep(sizeOfSelectedCultivator: Int) {
        if (sizeOfSelectedCultivator > 0) {
            when (loanDetailPackage?.loanPackageDataSchema?.properties?.collateralData?.items?.required?.contains(
                "isRequireCollateral"
            )) {
                true -> {
                    warningSnackBar(getString(R.string.label_on_development_proccess))
                    loanSubmissionNav.toCollateralPage()
                }

                else -> loanSubmissionNav.toLandSubmissionPage()
            }
        } else {
            warningSnackBar(getString(R.string.label_warning_choose_cultivator))
        }
    }

    private fun preBeforeMainApiHit() {
        viewModel.setStepperList(listOf())
        if (loanDetailPackage.isNotNull()) {
            when {/* ini masih harus disikusikan dengan qa dan PO terkait flow di existing
                loanDetailPackage?.loanPackageItemList.isNullOrEmpty() -> {
                    viewModel.setStepperList(listOf(StepperType.GOODS))
                }
                */
            }
            viewModel.initLoanPackage(loanDetailPackage)
        }
    }

    private fun checkMemberEligibility(item: LoanSubmissionBaseItem): Pair<Boolean, CheckableCultivator> {
        val data = convertToCheckableCultivator(item)
        return Pair(data.data.isEligible, data)
    }

    private fun convertToCheckableCultivator(item: LoanSubmissionBaseItem) =
        item.toCheckableCultivator()

    private fun showLack(item: LoanSubmissionBaseItem) {
        val data = convertToCheckableCultivator(item)
        val (isEligible, _) = checkMemberEligibility(item)

        val newRequiredFields = checkEligibility(data.data)

        if (!isEligible) {
            LackBottomSheetFragment(
                eligibleErrorMessage = data.data.isEligibleErrorMessage,
                true,
                data.data,
                loanDetailPackage,
                requiredFields = newRequiredFields
            ).show(childFragmentManager, LackBottomSheetFragment.TAG)
        }
    }
}
