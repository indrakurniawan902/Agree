package com.agree.ecosystem.monitoring.presentation.menu.historyactivity

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class HistoryActivityObserver(
    private val view: HistoryActivityDataContract,
    private val viewModel: HistoryActivityViewModel
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
    }
}
