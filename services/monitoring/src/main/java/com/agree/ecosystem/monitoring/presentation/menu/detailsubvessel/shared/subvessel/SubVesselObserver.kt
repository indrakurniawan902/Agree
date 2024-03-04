package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest

class SubVesselObserver(
    private val view: SubVesselDataContract,
    private val viewModel: SubVesselViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        observeSubVessel(owner)
        observeSubVesselId(owner)
        observeStatusSubVessel(owner)
    }

    private fun observeSubVesselId(owner: LifecycleOwner) {
        owner.lifecycleScope.launchWhenStarted {
            viewModel.subVesselId.collectLatest {
                view.onSubVesselIdChanged(it)
            }
        }
    }

    private fun observeSubVessel(owner: LifecycleOwner) {
        owner.lifecycleScope.launchWhenStarted {
            viewModel.subVessel.collectLatest {
                view.onSubVesselChanged(it)
            }
        }
    }

    private fun observeStatusSubVessel(owner: LifecycleOwner) {
        owner.lifecycleScope.launchWhenStarted {
            viewModel.statusSubVessel.collectLatest {
                view.onStatusSubVesselChanged(it)
            }
        }
    }
}
