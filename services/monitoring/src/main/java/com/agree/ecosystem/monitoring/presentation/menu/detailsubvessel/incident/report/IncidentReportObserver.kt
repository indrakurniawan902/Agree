package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident.report

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class IncidentReportObserver(
    private val view: IncidentReportDataContract,
    private val viewModel: IncidentReportViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.addIncidentComment.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onAddIncidentCommentLoading()
                is VmData.Success -> view.onAddIncidentCommentSuccess(it.data)
                is VmData.Failure -> view.onAddIncidentCommentFailed(it.throwable)
                is VmData.Empty -> view.onAddIncidentCommentEmpty(EmptyException())
            }
        }
        viewModel.getIncidentComment.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetIncidentCommentLoading()
                is VmData.Success -> view.onGetIncidentCommentSuccess(it.data)
                is VmData.Failure -> view.onGetIncidentCommentFailed(it.throwable)
                is VmData.Empty -> view.onGetIncidentCommentEmpty(EmptyException())
            }
        }
    }
}
