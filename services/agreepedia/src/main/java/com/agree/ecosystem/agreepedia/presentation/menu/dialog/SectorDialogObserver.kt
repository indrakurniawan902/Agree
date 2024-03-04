package com.agree.ecosystem.agreepedia.presentation.menu.dialog

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.agreepedia.presentation.menu.home.AgreepediaViewModel
import kotlinx.coroutines.launch

class SectorDialogObserver(
    private val view: SectorDialogDataContract,
    private val viewModel: AgreepediaViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        owner.lifecycleScope.launch {
            viewModel.filterSector.collect { view.onSectorChange(it) }
        }
        owner.lifecycleScope.launch {
            viewModel.filterSort.collect {
                view.onSortChange(it)
            }
        }
    }
}
