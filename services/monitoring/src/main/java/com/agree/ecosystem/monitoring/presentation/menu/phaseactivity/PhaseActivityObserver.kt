package com.agree.ecosystem.monitoring.presentation.menu.phaseactivity

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class PhaseActivityObserver(
    private val view: PhaseActivityDataContract,
    private val viewModel: PhaseActivityViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.phaseActivityList.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> { view.onGetPhaseActivityLoading() }
                is VmData.Success -> { view.onGetPhaseActivitySuccess(it.data) }
                is VmData.Failure -> Unit
                is VmData.Empty -> Unit
            }
        }
    }
}
