package com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class DetailActivitySopObserver(
    private val view: DetailActivitySopDataContract,
    private val viewModel: DetailActivitySopViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.detailSop.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Empty -> Unit
                is VmData.Failure -> {
                    view.onGetSopActivityDetailError(it.message)
                }
                is VmData.Loading -> Unit
                is VmData.Success -> {
                    view.onGetSopActivityDetailSuccess(data = it.data)
                }
                else -> Unit
            }
        }

        viewModel.validationActivityDetail.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Empty -> view.getValidationActivityDetailFinish()
                is VmData.Failure -> {
                    view.getValidationActivityDetailError(it.message)
                    view.getValidationActivityDetailFinish()
                }
                is VmData.Loading -> Unit
                is VmData.Success -> {
                    view.getValidationActivityDetailSuccess(it.data)
                    view.getValidationActivityDetailFinish()
                }
                else -> view.getValidationActivityDetailFinish()
            }
        }
    }
}
