package com.agree.ecosystem.utilities.presentation.menu.zone.province

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class ProvinceObserver(
    private val view: ProvinceDataContract,
    private val viewModel: ProvinceViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.provinces.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onLoading()
                is VmData.Success -> view.onGetProvinceSuccess(it.data)
                is VmData.Failure -> view.onGetProvinceFailed(it.throwable)
                is VmData.Empty -> Unit
            }
        }
    }
}
