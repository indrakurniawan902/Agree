package com.agree.ecosystem.monitoring.presentation.menu.phaseactivity.subphaseactivity

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class SubPhaseActivityObserver(
    private val view: SubPhaseActivityDataContract,
    private val viewModel: SubPhaseActivityViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.subPhaseActivity.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> Unit
                is VmData.Success -> { view.onGetSuccess(it.data) }
                is VmData.Failure -> Unit
                is VmData.Empty -> Unit
            }
        }
    }
}
