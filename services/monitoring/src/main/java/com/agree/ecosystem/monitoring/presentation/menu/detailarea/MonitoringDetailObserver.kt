package com.agree.ecosystem.monitoring.presentation.menu.detailarea

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class MonitoringDetailObserver(
    private val view: MonitoringDetailDataContract,
    private val viewModel: MonitoringDetailViewModel,
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.subVessel.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetListSubVesselLoading()
                is VmData.Success -> {
                    view.onGetListSubVesselSuccess(it.data)
                    viewModel.page++
                }
                is VmData.Failure -> view.onGetListSubVesselFailed(it.throwable)
                is VmData.Empty -> view.onGetListSubVesselEmpty()
            }
        }

        viewModel.loadMoreSubVessel.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onLoadMoreSubVesselLoading()
                is VmData.Success -> {
                    viewModel.page++
                    view.onLoadMoreSubVesselSuccess(it.data)
                }
                is VmData.Failure -> view.onLoadMoreSubVesselFailed()
                is VmData.Empty -> view.onLoadMoreSubVesselEmpty()
            }
        }

        viewModel.vessel.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetDetailVesselLoading()
                is VmData.Success -> {
                    view.onGetDetailVesselSuccess(it.data)
                }
                is VmData.Failure -> view.onGetDetailVesselFailed(it.throwable)
                is VmData.Empty -> view.onGetDetailVesselEmpty()
            }
        }
    }
}
