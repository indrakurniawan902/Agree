package com.agree.ecosystem.monitoring.presentation.menu.historyactivity.historyfilter

import androidx.recyclerview.widget.RecyclerView
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.FragmentListHistoryBinding
import com.agree.ecosystem.monitoring.databinding.LayoutEmptyListActivityBinding
import com.agree.ecosystem.monitoring.databinding.LayoutErrorListActivityBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitySopGroupByDate
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselObserver
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselViewModel
import com.agree.ecosystem.monitoring.utils.Constant
import com.devbase.presentation.recyclerview.endless.OnLoadMoreListener
import com.telkom.legion.component.viewstate.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryCompletedFragment :
    AgrFragment<FragmentListHistoryBinding>(),
    HistoryFilterDataContract,
    OnLoadMoreListener {
    override fun initUI() {
        super.initUI()
        binding.rvItemActivity.adapter = adapter
    }
    override fun initObserver() {
        super.initObserver()
        addObservers(
            HistoryFilterObserver(this, viewModel),
            SubVesselObserver(this, sharedVm)
        )
    }

    override fun onSubVesselIdChanged(id: String) {
        super.onSubVesselIdChanged(id)
        if (id.isNotEmpty()) {
            getListActivity(id)
        }
    }

    override fun getListActivity(id: String) {
        viewModel.getListActivity(Constant.FILTER_HISTORY_COMPLETED, id)
    }

    override fun getLoadMoreActivity(id: String) {
        viewModel.loadMoreActivity(Constant.FILTER_HISTORY_COMPLETED, id)
    }

    override fun onGetListActivityLoading() {
        binding.msvActivity.showLoadingLayout()
    }

    override fun onGetListActivitySuccess(data: List<ActivitySopGroupByDate>) {
        with(binding) {
            msvActivity.showDefaultLayout()
            adapter.apply {
                setLoadMoreListener(this@HistoryCompletedFragment)
                setEndlessScroll(rvItemActivity)
                resetEndlessScroll()
                adapter.clear()
                adapter.addOrUpdateAll(data)
            }
        }
    }

    override fun onGetListActivityEmpty() {
        with(binding) {
            msvActivity.showEmptyLayout()
            msvActivity.getView(ViewState.EMPTY)?.let {
                with(LayoutEmptyListActivityBinding.bind(it)) {
                    tvEmptyTitle.text = getString(R.string.label_empty_history_completed)
                    tvEmptyMessage.text = getString(R.string.label_empty_history_completed_desc)
                }
            }
        }
    }

    override fun onGetListActivityFailed() {
        with(binding) {
            msvActivity.showErrorLayout()
            msvActivity.getView(ViewState.ERROR)?.let {
                with(LayoutErrorListActivityBinding.bind(it)) {
                    tvErrorTitle.text = getString(R.string.error_load_history_completed)
                    tvErrorMessage.text = getString(R.string.error_load_history_completed_desc)
                    btnRetry.setOnClickListener { getListActivity(subVesselId) }
                }
            }
        }
    }

    override fun onLoadMoreSuccess(data: List<ActivitySopGroupByDate>) {
        if (adapter.itemCount > 0) {
            adapter.hideLoadMoreLoading()
        }
        adapter.addOrUpdateAll(data)
    }

    override fun onLoadMoreEmpty() {
        adapter.finishLoadMore()
    }

    override fun onLoadMoreFailed() {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreError()
        }
    }

    override fun onLoadMoreLoading() {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreLoading()
        }
    }

    override fun onLoadMore(skip: Int?, limit: Int?, totalItemsCount: Int?, view: RecyclerView?) {
        getLoadMoreActivity(sharedVm.getSubVesselId())
    }

    override fun onLoadMoreRetryButtonClicked(skip: Int?, limit: Int?) {
        adapter.hideLoadMoreLoading()
        getLoadMoreActivity(sharedVm.getSubVesselId())
    }

    private val adapter: HistoryFilterAdapter by lazy {
        HistoryFilterAdapter {}
    }

    private val viewModel: HistoryFilterViewModel by viewModel()
    private val sharedVm: SubVesselViewModel by sharedViewModel()
    private var subVesselId: String = ""
}
