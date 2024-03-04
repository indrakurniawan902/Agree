package com.agree.ecosystem.monitoring.presentation.menu.additionalactivities

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class AdditionalActivitiesObserver(
    private val view: AdditionalActivitiesDataContract,
    private val viewModel: AdditionalActivitiesViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.statusForm.observe(owner) {
            view.setFormEnabled(it)
        }
    }
}
