package com.agree.ecosystem.common.presentation.menu.monitoring.subvessels

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class SubVesselObserver(
    private val view: SubVesselDataContract,
    private val viewModel: SubVesselViewModel,
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
    }
}
