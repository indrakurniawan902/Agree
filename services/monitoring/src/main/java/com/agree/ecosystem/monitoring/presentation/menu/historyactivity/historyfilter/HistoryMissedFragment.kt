package com.agree.ecosystem.monitoring.presentation.menu.historyactivity.historyfilter

import androidx.recyclerview.widget.RecyclerView
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.FragmentListHistoryBinding
import com.agree.ecosystem.monitoring.databinding.LayoutEmptyListActivityBinding
import com.agree.ecosystem.monitoring.databinding.LayoutErrorListActivityBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitiesSopMissed
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselObserver
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselViewModel
import com.devbase.presentation.recyclerview.endless.OnLoadMoreListener
import com.telkom.legion.component.viewstate.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryMissedFragment :
    AgrFragment<FragmentListHistoryBinding>(),
    HistoryMissedFilterDataContract,
    OnLoadMoreListener {
    override fun initUI() {
        super.initUI()
        binding.rvItemActivity.adapter = adapter
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(
            HistoryMissedFilterObserver(this@HistoryMissedFragment, viewModel),
            SubVesselObserver(this, sharedVM)
        )
    }

    override fun onGetListActivityLoading() {
        binding.msvActivity.showLoadingLayout()
    }

    override fun onGetListActivitySuccess(data: List<ActivitiesSopMissed>) {
        with(binding) {
            msvActivity.showDefaultLayout()
            adapter.apply {
                setLoadMoreListener(this@HistoryMissedFragment)
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
                    tvEmptyTitle.text = getString(R.string.label_empty_history_missed)
                    tvEmptyMessage.text = getString(R.string.label_empty_history_missed_desc)
                }
            }
        }
    }

    override fun onGetListActivityFailed() {
        with(binding) {
            msvActivity.showErrorLayout()
            msvActivity.getView(ViewState.ERROR)?.let {
                with(LayoutErrorListActivityBinding.bind(it)) {
                    tvErrorTitle.text = getString(R.string.error_load_history_missed)
                    tvErrorMessage.text = getString(R.string.error_load_history_missed_desc)
                    btnRetry.setOnClickListener { getListActivity(subVesselId) }
                }
            }
        }
    }

    override fun getListActivity(id: String) {
        viewModel.getListActivity(id)
    }

    override fun getLoadMoreActivity(id: String) {
        viewModel.loadMoreActivity(id)
    }

    override fun onSubVesselIdChanged(id: String) {
        super.onSubVesselIdChanged(id)
        if (id.isNotEmpty()) {
            subVesselId = id
            getListActivity(id)
        }
    }

    override fun onLoadMoreSuccess(data: List<ActivitiesSopMissed>) {
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
        getLoadMoreActivity(sharedVM.getSubVesselId())
    }

    override fun onLoadMoreRetryButtonClicked(skip: Int?, limit: Int?) {
        adapter.hideLoadMoreLoading()
        getLoadMoreActivity(sharedVM.getSubVesselId())
    }

    private val adapter: HistoryMissedFilterAdapter by lazy {
        HistoryMissedFilterAdapter {}
    }
    private val viewModel: HistoryMissedFilterViewModel by viewModel()
    private val sharedVM: SubVesselViewModel by sharedViewModel()
    private var subVesselId: String = ""
}
