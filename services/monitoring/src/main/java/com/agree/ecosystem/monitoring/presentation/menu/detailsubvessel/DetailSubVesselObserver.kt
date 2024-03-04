package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class DetailSubVesselObserver(
    private val view: DetailSubVesselDataContract,
    private val viewModel: DetailSubVesselViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        viewModel.subVessel.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetDetailSubVesselLoading()
                is VmData.Success -> view.onGetDetailSubVesselSuccess(it.data)
                is VmData.Failure -> view.onGetDetailSubVesselFailed(it.throwable)
                is VmData.Empty -> view.onGetDetailSubVesselFailed(EmptyException())
            }
        }

        viewModel.endCycle.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onEndCycleLoading()
                is VmData.Success -> view.onEndCycleSuccess(it.data)
                is VmData.Failure -> view.onGetDetailSubVesselFailed(it.throwable)
                is VmData.Empty -> view.onGetDetailSubVesselFailed(EmptyException())
            }
        }
    }
}
