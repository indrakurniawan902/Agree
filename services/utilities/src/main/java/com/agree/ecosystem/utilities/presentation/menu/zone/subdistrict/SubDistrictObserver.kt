package com.agree.ecosystem.utilities.presentation.menu.zone.subdistrict

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class SubDistrictObserver(
    private val view: SubDistrictDataContract,
    private val viewModel: SubDistrictViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.subDistrict.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onLoading()
                is VmData.Success -> view.onGetSubDistrictSuccess(it.data)
                is VmData.Failure -> view.onGetSubDistrictFailed(it.throwable)
                is VmData.Empty -> Unit
            }
        }
    }
}
