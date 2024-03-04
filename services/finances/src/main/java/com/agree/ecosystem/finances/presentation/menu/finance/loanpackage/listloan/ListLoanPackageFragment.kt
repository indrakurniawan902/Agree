package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.listloan

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.domain.reqres.model.FinanceCustomMapData
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.finances.R
import com.agree.ecosystem.finances.databinding.FragmentListLoanPackageBinding
import com.agree.ecosystem.finances.databinding.LayoutEmptyListListLoanPackageBinding
import com.agree.ecosystem.finances.domain.reqres.model.FilterKeyData
import com.agree.ecosystem.finances.domain.reqres.model.LoanPackage
import com.agree.ecosystem.finances.domain.reqres.model.LoanPackagePaymentType
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.bottomsheetsectorpartnership.SectorPartnershipBottomSheet
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.listloan.adapters.ListLoanPackageAdapter
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.listloan.filter.LoanPackageFilterFragment
import com.agree.ecosystem.finances.presentation.navigation.MenuNavigation
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.agree.ecosystem.utilities.presentation.menu.sectorsdialog.SectorsDataContract
import com.agree.ecosystem.utilities.presentation.menu.sectorsdialog.SectorsObserver
import com.agree.ecosystem.utilities.presentation.menu.sectorsdialog.SectorsViewModel
import com.agree.ui.UiKitAttrs
import com.agree.ui.UiKitDimens
import com.agree.ui.snackbar.errorSnackBar
import com.devbase.presentation.recyclerview.endless.OnLoadMoreListener
import com.jakewharton.rxbinding3.widget.textChanges
import com.oratakashi.viewbinding.core.tools.gone
import com.telkom.legion.component.viewstate.ViewState
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showEmptyLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.concurrent.TimeUnit

class ListLoanPackageFragment : AgrFragment<FragmentListLoanPackageBinding>(),
    ListLoanPackageDataContract, OnLoadMoreListener, SectorsDataContract {

    private val loanDatas: MutableList<LoanPackage> by lazy {
        ArrayList()
    }

    private val sectorDatas: MutableList<SubSector> = mutableListOf()
    private val financeCustomMapData: MutableList<FinanceCustomMapData> = mutableListOf()

    private var query = ""
    private var isPartner = true

    private var isRequestingLoadMoreData = false
    private var isLoadMoreFinish = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObservers(
            ListLoanPackageObserver(this, viewModel),
            SectorsObserver(this, subSectorSharedViewModel)
        )
//        addObservers(
//            ListLoanPackageObserver(this, viewModel),
//            SectorsObserver(this, subSectorSharedViewModel)
//        )
    }

    override fun onStart() {
        super.onStart()
        if (isPartner) {
            fetchlistLoanPackage()
        } else {
            binding.msvLoanPackage.showEmptyLayout()
            inflateEmptyLayout()
        }
    }

    override fun initUI() {
        super.initUI()
        sectorDatas.clear()
        with(binding) {
            rvLoanPackage.adapter = adapter
            if (loanDatas.size > 0) addChips()
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            cvFilter.setOnClickListener {
                sectorDialog()
            }

            ivFilter.setOnClickListener {
                with(cvFilter) {
                    performClick()
                    isPressed = true
                }
            }

            etSearch.editText?.let {
                it.textChanges().skipInitialValue().debounce(800, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe { text ->
                        query = text.toString()
//                        fetchlistLoanPackage()
                    }
            }
        }
        getSubSectors()
    }

    private fun sectorDialog() {
        LoanPackageFilterFragment(financeCustomMapData, financeCustomMapData.joinToString(", ")) {
//            financeCustomMapData.clear()
//            financeCustomMapData.addAll(it)
            addChips()
        }.show(childFragmentManager, "dialog")
    }

    private fun addChips() {
        with(binding) {
//            cgLoanPackage.addAll(loanDatas.map { it.sector })
            cgLoanPackage.setOnCloseIconClickListener { selectedLoan ->

            }
        }
    }

    private fun setFilterButtonColor(isFilter: Boolean) {
        if (isAdded) {
            with(binding) {
                ivFilter.setColorFilter(
                    requireContext().getAttrsValue(if (isFilter) UiKitAttrs.colorPrimary else UiKitAttrs.black_900),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
                cvFilter.strokeColor =
                    requireContext().getAttrsValue(if (isFilter) UiKitAttrs.colorPrimary else UiKitAttrs.black_600)
                cvFilter.strokeWidth =
                    resources.getDimension(if (isFilter) UiKitDimens.dimen_2dp else UiKitDimens.dimen_1dp)
                        .toInt()
            }
        }
    }

    override fun initData() {
        super.initData()
//        if (isPartner) {
//            fetchlistLoanPackage()
//        } else {
//            binding.msvLoanPackage.showEmptyLayout()
//            inflateEmptyLayout()
//        }
    }

    override fun initObserver() {
        super.initObserver()
        Timber.tag("LIFEE").v("INITOBSERVER LIST")

    }


    override fun fetchlistLoanPackage() {
        viewModel.fetchlistLoanPackage()
        setFilterButtonColor(loanDatas.isNotEmpty())
    }

    override fun fetchLoadMoreLoanPackage() {
        isRequestingLoadMoreData = true
        viewModel.fetchLoadMoreLoanPackage()
    }

    override fun listLoanPackageOnSuccess(data: List<LoanPackage>) {
        with(binding) {
            msvLoanPackage.showDefaultLayout()
            adapter.apply {
                setLoadMoreListener(this@ListLoanPackageFragment)
                setEndlessScroll(rvLoanPackage)
                resetEndlessScroll()
                clear()
                addOrUpdateAll(data)
            }
        }
//        listLoanPackageOnEmpty()
    }

    override fun listLoanPackageOnLoading() {
        binding.msvLoanPackage.showLoadingLayout()
    }

    override fun listLoanPackageOnEmpty() {
        binding.msvLoanPackage.showEmptyLayout()
        inflateEmptyLayout(isPartner)
    }

    private fun inflateEmptyLayout(isPartner: Boolean = false) {
        with(binding) {
            msvLoanPackage.getView(ViewState.EMPTY).let {
                val emptyBinding = LayoutEmptyListListLoanPackageBinding.bind(it)
                bindingEmptyLayout(emptyBinding, isPartner)
            }
        }
    }

    private fun bindingEmptyLayout(
        binding: LayoutEmptyListListLoanPackageBinding, isPartner: Boolean
    ) {
        with(binding) {
            if (isPartner) {
                this@ListLoanPackageFragment.binding.etSearch.placeHolder =
                    getString(R.string.label_hint_find_loan_package)
                tvTitleEmptyListPackage.text =
                    getString(R.string.label_title_empty_list_list_loan_package)
                tvDescEmptyListPackage.text =
                    getString(R.string.label_description_loan_package_not_evailable)
                btnStartPartnership.text = getString(R.string.label_submit_capital)
                btnStartPartnership.gone()
            } else {
                this@ListLoanPackageFragment.binding.etSearch.placeHolder =
                    getString(R.string.label_hint_find_partnership)
                tvTitleEmptyListPackage.text =
                    getString(R.string.label_title_empty_loan_package_not_partner)
                tvDescEmptyListPackage.text =
                    getString(R.string.label_description_loan_package_not_partner)
                btnStartPartnership.text = getString(R.string.label_button_start_partnering)
                btnStartPartnership.setOnClickListener {
                    actionNotYetPartner()
                }
            }
        }
    }

    override fun onGetSubSectorsLoading() {}

    override fun onGetSubSectorsSuccess(data: List<com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector>) {
        sectorDatas.addAll(data)

        financeCustomMapData.clear()
        financeCustomMapData.addAll(createItemForFilter(data.map {
            FinanceCustomMapData(FilterKeyData.SECTOR_DATA.value, it.getFullSectorName())
        }
        ))
    }

    private fun createItemForFilter(dataSector: List<FinanceCustomMapData>): List<FinanceCustomMapData> {
        val typeFinanceCustomMapData = mutableListOf(
            FinanceCustomMapData(
                FilterKeyData.TYPE_LOAN_DATA.value,
                LoanPackagePaymentType.TUNAI.value
            ),
            FinanceCustomMapData(
                FilterKeyData.TYPE_LOAN_DATA.value,
                LoanPackagePaymentType.NONTUNAI.value
            )
        )
        return typeFinanceCustomMapData + dataSector
    }

    override fun onGetSubSectorsFailed(e: Throwable?) {}

    override fun getSubSectors() {
        subSectorSharedViewModel.getSubSectors()
    }

    private fun actionNotYetPartner() {
        SectorPartnershipBottomSheet({
            val selected = arrayOf(it)
            fromListLoanToCompanies(selected)
        }, sectorDatas).show(childFragmentManager, "sectorPartnershipLoanSubmission")
    }

    private fun fromListLoanToCompanies(subSector: Array<com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector>) {
        menuNav.fromListPackageToCompanies(subSector)
    }

    override fun listLoanPackageOnError(e: Throwable?) {
        errorSnackBar(e?.message.toString())
    }

    override fun loadMorelistLoanPackageOnSuccess(data: List<LoanPackage>) {
        if (adapter.itemCount > 0) {
            adapter.hideLoadMoreLoading()
        }
        adapter.addOrUpdateAll(data)

        isLoadMoreFinish = true
        isRequestingLoadMoreData = false
    }

    override fun loadMorelistLoanPackageOnLoading() {
        if (adapter.itemCount > 0) adapter.showLoadMoreLoading()
    }

    override fun loadMorelistLoanPackageOnError(e: Throwable?) {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreError()
        }
        isLoadMoreFinish = true
        isRequestingLoadMoreData = false
    }

    override fun loadMorelistLoanPackageOnEmpty() {
        adapter.finishLoadMore()
        isLoadMoreFinish = true
        isRequestingLoadMoreData = false
    }

    override fun onLoadMore(skip: Int?, limit: Int?, totalItemsCount: Int?, view: RecyclerView?) {
        if (isLoadMoreFinish == true && isRequestingLoadMoreData == false) {
            fetchLoadMoreLoanPackage()
        }
    }

    override fun onLoadMoreRetryButtonClicked(skip: Int?, limit: Int?) {
        adapter.hideLoadMoreLoading()
        fetchLoadMoreLoanPackage()
    }


    private val prefs: AgrPrefUsecase by inject()
    private val menuNav: MenuNavigation by navigation { activity }
    private val viewModel: ListLoanPackageViewModel by viewModel()
    private val sharedViewModel: ListLoanPackageViewModel by activityViewModels()
    private val subSectorSharedViewModel: SectorsViewModel by sharedViewModel()
    private val adapter: ListLoanPackageAdapter by lazy {
        ListLoanPackageAdapter(menuNav)
    }
}
