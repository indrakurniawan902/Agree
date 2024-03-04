package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.dialog.addactivity

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class AddActivityObserver(
    private val view: AddActivityDataContract,
    private val viewModel: AddActivityViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.insertSopDate.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onPostInsertSopDateLoading()
                is VmData.Success -> view.onPostInsertSopDateSuccess(it.data)
                is VmData.Failure -> view.onPostInsertSopDateFailed(it.throwable)
                is VmData.Empty -> view.onPostInsertSopDateFailed(EmptyException())
            }
        }
    }
}
