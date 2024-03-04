package com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop.individual

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class IndividualMonitoringObserver(
    private val view: IndividualMonitoringDataContract,
    private val viewModel: IndividualMonitoringViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        viewModel.individualMonitoringIds.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onIndividualLoading()
                is VmData.Success -> view.onIndividualSuccess(it.data)
                is VmData.Failure -> view.onIndividualError(it.throwable)
                is VmData.Empty -> view.onIndividualEmpty()
            }
        }

        viewModel.loadMoreindividualMonitoringIds.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onLoadMoreIndividualLoading()
                is VmData.Success -> view.onLoadMoreIndividualSuccess(it.data)
                is VmData.Failure -> view.onLoadMoreIndividualError(it.throwable)
                is VmData.Empty -> view.onLoadMoreIndividualEmpty()
            }
        }

        viewModel.entryPoint.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onEntryPointLoading()
                is VmData.Success -> view.onEntryPointSuccess(it.data)
                is VmData.Failure -> view.onEntryPointError(it.throwable)
                is VmData.Empty -> Unit
            }
        }
    }
}
