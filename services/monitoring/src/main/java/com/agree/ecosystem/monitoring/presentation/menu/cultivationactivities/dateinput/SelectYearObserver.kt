package com.agree.ecosystem.monitoring.presentation.menu.cultivationactivities.dateinput

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class SelectYearObserver(
    private val view: SelectYearDataContract,
    private val viewModel: SelectYearViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.listYear.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> Unit
                is VmData.Success -> { view.getListYear(it.data) }
                is VmData.Failure -> Unit
                is VmData.Empty -> Unit
            }
        }
    }
}
