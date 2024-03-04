package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.activities

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class ActivitiesObserver(
    private val view: ActivitiesDataContract,
    private val viewModel: ActivitiesViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.activities.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onActivityLoading()
                is VmData.Success -> view.onActivitySuccess(it.data)
                is VmData.Failure -> Unit
                is VmData.Empty -> view.onActivityEmpty()
            }
        }
    }
}
