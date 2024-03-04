package com.agree.ecosystem.utilities.presentation.menu.zone.district

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class DistrictObserver(
    private val view: DistrictDataContract,
    private val viewModel: DistrictViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.district.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onLoading()
                is VmData.Success -> view.onGetDistrictSuccess(it.data)
                is VmData.Failure -> view.onGetDistrictFailed(it.throwable)
                is VmData.Empty -> Unit
            }
        }
    }
}
