package com.agree.ecosystem.monitoring.presentation.menu.insertupdateadditionalactivitysop

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class InsertUpdateAdditionalActivityObserver(
    val view: InsertUpdateAdditionalActivityDataContract,
    val viewModel: InsertUpdateAdditionalActivitySopViewModel
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        viewModel.toggleStatus.observe(owner) {
            view.setFormEnabled(it)
        }

        viewModel.insertDetailResponse.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Empty -> Unit
                is VmData.Failure -> {
                    view.onInsertAdditionalActivityError(it.throwable)
                }
                is VmData.Loading -> {
                    view.onInsertAdditionalActivityLoading()
                }
                is VmData.Success -> {
                    view.onInsertAdditionalActivitySuccess(it.data)
                }
                else -> Unit
            }
        }

        viewModel.formSchemaData.observe(owner) {
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

        viewModel.updateDetailResponse.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Empty -> Unit
                is VmData.Failure -> {
                    view.onUpdateAdditionalActivityError(it.throwable)
                }
                is VmData.Loading -> {
                    view.onUpdateAdditionalActivityLoading()
                }
                is VmData.Success -> {
                    view.onUpdateAdditionalActivitySuccess()
                }
                else -> Unit
            }
        }
    }
}
