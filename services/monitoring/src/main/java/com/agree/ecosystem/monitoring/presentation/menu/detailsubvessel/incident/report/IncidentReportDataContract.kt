package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident.report

import com.agree.ecosystem.monitoring.domain.reqres.model.incidentcomment.IncidentComment

interface IncidentReportDataContract {

    fun onAddIncidentCommentLoading()

    fun onAddIncidentCommentSuccess(data: IncidentComment)

    fun onAddIncidentCommentFailed(e: Throwable?) {
        // Do Nothing
    }

    fun onAddIncidentCommentEmpty(e: Throwable?) {
        // Do Nothing
    }

    fun onGetIncidentCommentLoading()

    fun onGetIncidentCommentSuccess(data: List<IncidentComment>)

    fun onGetIncidentCommentFailed(e: Throwable?) {
        // Do Nothing
    }

    fun onGetIncidentCommentEmpty(e: Throwable?) {
        // Do Nothing
    }
}
