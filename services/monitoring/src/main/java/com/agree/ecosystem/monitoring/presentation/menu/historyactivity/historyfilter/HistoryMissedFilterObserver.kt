package com.agree.ecosystem.monitoring.presentation.menu.historyactivity.historyfilter

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class HistoryMissedFilterObserver(
    private val view: HistoryMissedFilterDataContract,
    private val viewModel: HistoryMissedFilterViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        viewModel.activity.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetListActivityLoading()
                is VmData.Success -> view.onGetListActivitySuccess(it.data)
                is VmData.Failure -> view.onGetListActivityFailed()
                is VmData.Empty -> view.onGetListActivityEmpty()
            }
        }
        viewModel.loadMoreActivity.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Success -> view.onLoadMoreSuccess(it.data)
                is VmData.Loading -> view.onLoadMoreLoading()
                is VmData.Empty -> view.onLoadMoreEmpty()
                is VmData.Failure -> view.onLoadMoreFailed()
            }
        }
    }
}
