package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.transaction

import androidx.recyclerview.widget.RecyclerView
import com.agree.ecosystem.core.utils.base.AgrChildFragment
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.extension.toCurrencyFormat
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.FragmentTransactionBinding
import com.agree.ecosystem.monitoring.databinding.LayoutEmptyCultivationActivityBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVessel
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.Transaction
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.TransactionSummary
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.dialog.addactivity.AddActivityBottomSheet
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselObserver
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselViewModel
import com.agree.ecosystem.monitoring.presentation.navigation.DetailSubVesselNavigation
import com.agree.ecosystem.monitoring.utils.Constant
import com.agree.ui.snackbar.errorSnackBar
import com.devbase.presentation.recyclerview.endless.OnLoadMoreListener
import com.devbase.utils.ext.gone
import com.devbase.utils.ext.visible
import com.telkom.legion.component.viewstate.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransactionFragment :
    AgrChildFragment<FragmentTransactionBinding>(),
    TransactionDataContract,
    OnLoadMoreListener {

    private var isRequestingData = false
    private var isLoadMoreFinish = false

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvTransaction.adapter = adapter
            rvTransaction.isNestedScrollingEnabled = false
            with(root) {
                viewTreeObserver.addOnScrollChangedListener {
                    val view = getChildAt(childCount - 1)
                    if (view.bottom - (height + scrollY) == 0 && !isRequestingData && !isLoadMoreFinish) {
                        fetchLoadMoreTransactions()
                    }
                }
            }
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(SubVesselObserver(this, sharedVm))
        addObservers(TransactionObserver(this, viewModel))
    }

    override fun onResume() {
        super.onResume()
        fetchTransactionList(sharedVm.getSubVesselId())
        fetchTransactionSummary(sharedVm.getSubVesselId())
    }

    override fun fetchTransactionList(id: String) {
        if (!isRequestingData) {
            viewModel.getListTransaction(id)
            isRequestingData = true
            isLoadMoreFinish = false
        }
    }

    override fun fetchTransactionSummary(id: String) {
        viewModel.getTransactionSummary(id)
    }

    override fun onGetTransactionsLoading() {
        binding.msvTransaction.showLoadingLayout()
    }

    override fun onGetTransactionsSuccess(data: List<Transaction>) {
        with(binding) {
            msvTransaction.showDefaultLayout()
            adapter.apply {
                setLoadMoreListener(this@TransactionFragment)
                setEndlessScroll(rvTransaction)
                resetEndlessScroll()
                clear()
                addOrUpdateAll(data)
            }
        }
        isRequestingData = false
    }

    override fun onGetTransactionsFailed(e: Throwable?) {
        errorSnackBar(e?.message.toString())
        isRequestingData = false
        isLoadMoreFinish = true
    }

    override fun onTransactionsEmpty() {
        with(binding) {
            if (sharedVm.getStatusSubVessel() == Constant.STATUS_ACTIVE) {
                msvTransaction.showEmptyLayout()
                cvSummary.gone()
                sharedVm.setActivitiesState(false)
            } else {
                msvTransaction.showErrorLayout()
                sharedVm.setActivitiesState(true)
                with(LayoutEmptyCultivationActivityBinding.bind(msvTransaction.getView(ViewState.ERROR))) {
                    tvTitleEmpty.text = getString(R.string.label_inactive_sub_vessel, sharedVm.getSubVesselName())
                    tvDescEmpty.text = getString(
                        R.string.label_please_activate_livestock_to_add, sharedVm.getSubVesselName(),
                        getString(R.string.label_transaction).lowercase()
                    )
                    btnRegister.text = getString(R.string.label_activate_the_sub_vessel_button, sharedVm.getSubVesselName())
                    btnRegister.setOnClickListener {
                        sharedVm.getSubVessel()?.let { subVessel ->
                            AddActivityBottomSheet(subVessel, callback = {
                                sharedVm.setStatusSubVessel(Constant.STATUS_ACTIVE)
                                sharedVm.setActivitiesState(true)
                                fetchTransactionList(sharedVm.getSubVesselId())
                            }).showNow(childFragmentManager, "dialog")
                        }
                    }
                }
            }
        }
        isRequestingData = false
        isLoadMoreFinish = true
    }

    override fun fetchLoadMoreTransactions() {
        isRequestingData = true
        viewModel.loadMoreListTransaction(sharedVm.getSubVesselId())
    }

    override fun onLoadMoreSuccess(data: List<Transaction>) {
        if (adapter.itemCount > 0) {
            adapter.hideLoadMoreLoading()
        }
        adapter.addOrUpdateAll(data)
        isRequestingData = false
    }

    override fun onLoadMoreLoading() {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreLoading()
        }
    }

    override fun onLoadMoreFailed() {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreError()
        }
        isRequestingData = false
    }

    override fun onLoadMoreEmpty() {
        adapter.finishLoadMore()
        isRequestingData = false
        isLoadMoreFinish = true
    }

    override fun onTransactionSummaryLoading() {
        binding.cvSummary.gone()
    }

    override fun onTransactionSummarySuccess(data: TransactionSummary) {
        with(binding) {
            if (!data.isEmpty()) {
                cvSummary.visible()
                tvResult.text = getString(
                    R.string.label_rupiah_value,
                    if (data.fee == 0L) "-" else data.fee.toDouble().toCurrencyFormat()
                )
                tvWeight.text = getString(
                    R.string.label_weight_value,
                    if (data.netto.isNotEmpty()) (data.netto.toDouble() * 1000).toString() else "-"
                )
            }
        }
    }

    override fun onTransactionSummaryFailed(e: Throwable?) {
        errorSnackBar(e?.message.toString())
    }

    override fun onSubVesselIdChanged(id: String) {
        if (id.isNotEmpty()) {
            fetchTransactionList(id)
            fetchTransactionSummary(id)
        }
    }

    override fun onActionButtonTriggered(data: DetailSubVessel) {
        menuDetailSubVessel.fromDetailSubVesselToInsertTransaction(null)
    }

    override fun onLoadMore(skip: Int?, limit: Int?, totalItemsCount: Int?, view: RecyclerView?) {
//        fetchLoadMoreTransactions()
    }

    override fun onLoadMoreRetryButtonClicked(skip: Int?, limit: Int?) {
        adapter.hideLoadMoreLoading()
        fetchLoadMoreTransactions()
    }

    private val adapter: TransactionAdapter by lazy {
        TransactionAdapter {
            menuDetailSubVessel.fromDetailSubVesselToTransactionDetail(it)
        }
    }
    private val sharedVm: SubVesselViewModel by sharedViewModel()
    private val viewModel: TransactionViewModel by viewModel()
    private val menuDetailSubVessel: DetailSubVesselNavigation by navigation { activity }
}
