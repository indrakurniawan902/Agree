package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class IncidentObserver(
    private val view: IncidentDataContract,
    private val viewModel: IncidentViewModel
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        viewModel.incident.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onIncidentLoading()
                is VmData.Success -> {
                    view.onIncidentSuccess(it.data)
                    viewModel.page++
                }
                is VmData.Failure -> view.onIncidentFailed(it.throwable)
                is VmData.Empty -> view.onIncidentEmpty()
            }
        }

        viewModel.loadMoreIncident.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onLoadMoreIncidentLoading()
                is VmData.Success -> {
                    view.onLoadMoreIncidentSuccess(it.data)
                    viewModel.page++
                }
                is VmData.Failure -> view.onLoadMoreIncidentFailed(it.throwable)
                is VmData.Empty -> view.onLoadMoreIncidentEmpty()
            }
        }
    }
}
