package com.agree.ecosystem.partnership.presentation.menu.detailvessel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class DetailVesselObserver(
    private val view: DetailVesselDataContract,
    private val viewModel: DetailVesselViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.listSubVessel.observe(owner) {
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
        viewModel.loadMoreListSubVessel.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onLoadMoreLoading()
                is VmData.Success -> {
                    viewModel.page++
                    view.onLoadMoreSuccess(it.data)
                }
                is VmData.Failure -> view.onLoadMoreFailed()
                is VmData.Empty -> view.onLoadMoreEmpty()
            }
        }
    }
}
