package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class AddIncidentObserver(
    private val view: AddIncidentDataContract,
    private val viewModel: AddIncidentViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.addIncident.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onAddIncidentLoading()
                is VmData.Success -> view.onAddIncidentSuccess()
                is VmData.Failure -> view.onAddIncidentFailed(it.throwable)
                is VmData.Empty -> view.onAddIncidentFailed(EmptyException())
            }
        }
        viewModel.listCategory.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetIncidentCategoriesLoading()
                is VmData.Success -> view.onGetIncidentCategoriesSuccess(it.data)
                is VmData.Failure -> view.onGetIncidentCategoriesFailed(it.throwable)
                is VmData.Empty -> view.onGetIncidentCategoriesFailed(EmptyException())
            }
        }
    }
}
