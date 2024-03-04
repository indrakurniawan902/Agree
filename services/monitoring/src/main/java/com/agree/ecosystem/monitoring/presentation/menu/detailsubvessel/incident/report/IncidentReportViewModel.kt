package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident.report

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.monitoring.data.reqres.model.incidentcomment.AddIncidentCommentBodyPost
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.agree.ecosystem.monitoring.domain.reqres.model.incidentcomment.IncidentComment
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class IncidentReportViewModel(
    private val usecase: MonitoringUseCase
) : DevViewModel() {
    private val _addIncidentComment = DevData<IncidentComment>().apply { default() }
    val addIncidentComment = _addIncidentComment.immutable()

    private val _getIncidentComment = DevData<List<IncidentComment>>().apply { default() }
    val getIncidentComment = _getIncidentComment.immutable()

    fun addIncidentComment(data: AddIncidentCommentBodyPost) {
        usecase.addIncidentComment(
            data
        ).setHandler(_addIncidentComment).let { return@let disposable::add }
    }

    fun getListIncidentComment(activityId: String) {
        usecase.getListIncidentComment(activityId).setHandler(_getIncidentComment)
            .let { return@let disposable::add }
    }
}
