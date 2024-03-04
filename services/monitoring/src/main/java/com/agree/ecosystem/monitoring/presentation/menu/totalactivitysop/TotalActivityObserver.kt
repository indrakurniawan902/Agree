package com.agree.ecosystem.monitoring.presentation.menu.totalactivitysop

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class TotalActivityObserver(
    private val view: TotalActivityDataContract,
    private val viewModel: TotalActivityViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.totalActivity.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetTotalActivityLoading()
                is VmData.Success -> view.onGetTotalActivitySuccess(it.data)
                is VmData.Failure -> Unit
                is VmData.Empty -> view.onGetTotalActivityEmpty()
            }
        }
    }
}
