package com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class DetailActivitySopFormObserver(
    private val view: DetailActivitySopFormDataContract,
    private val viewModel: DetailActivitySopViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        viewModel.toggleStatus.observe(owner) {
            view.setFormEnabled(it)
        }

        viewModel.updateDetailResponse.observe(owner) {
            when (it) {
                is VmData.Loading -> {
                    view.onUpdateSopActivityDetailLoading()
                }
                is VmData.Default -> Unit
                is VmData.Empty -> Unit
                is VmData.Success -> {
                    view.onUpdateSopActivityDetailSuccess()
                }
                is VmData.Failure -> {
                    view.onUpdateSopActivityDetailError(it.throwable)
                }
            }
        }

        viewModel.insertDetailResponse.observe(owner) {
            when (it) {
                is VmData.Loading -> {
                    view.onInsertSopActivityDetailLoading()
                }
                is VmData.Default -> Unit
                is VmData.Empty -> Unit
                is VmData.Success -> {
                    view.onInsertSopActivityDetailSuccess()
                }
                is VmData.Failure -> {
                    view.onInsertSopActivityDetailError(it.throwable)
                }
            }
        }

        viewModel.updateDetailResponseSop.observe(owner) {
            when (it) {
                is VmData.Loading -> {
                    view.onUpdateActivityDetailSopLoading()
                }
                is VmData.Default -> Unit
                is VmData.Empty -> Unit
                is VmData.Success -> {
                    view.onUpdateActivityDetailSopSuccess()
                }
                is VmData.Failure -> {
                    view.onUpdateActivityDetailSopError(it.throwable)
                }
            }
        }
    }
}
