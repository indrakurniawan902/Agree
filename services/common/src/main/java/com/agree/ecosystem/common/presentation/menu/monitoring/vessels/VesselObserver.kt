package com.agree.ecosystem.common.presentation.menu.monitoring.vessels

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class VesselObserver(
    private val view: VesselDataContract,
    private val viewModel: VesselViewModel,
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.vessel.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetListVesselLoading()
                is VmData.Success -> {
                    view.onGetListVesselSuccess(it.data)
                    viewModel.page++
                }
                is VmData.Failure -> view.onGetListVesselFailed(it.throwable)
                is VmData.Empty -> view.onGetListVesselEmpty()
            }
        }

        viewModel.loadMoreVessel.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onLoadMoreVesselLoading()
                is VmData.Success -> {
                    viewModel.page++
                    view.onLoadMoreVesselSuccess(it.data)
                }
                is VmData.Failure -> view.onLoadMoreVesselFailed()
                is VmData.Empty -> view.onLoadMoreVesselEmpty()
            }
        }
    }
}
