package com.agree.ecosystem.utilities.presentation.menu.zone.village

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class VillageObserver(
    private val view: VillageDataContract,
    private val viewModel: VillageViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.village.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onLoading()
                is VmData.Success -> view.onGetVillageSuccess(it.data)
                is VmData.Failure -> view.onGetVillageFailed(it.throwable)
                is VmData.Empty -> Unit
            }
        }
    }
}
