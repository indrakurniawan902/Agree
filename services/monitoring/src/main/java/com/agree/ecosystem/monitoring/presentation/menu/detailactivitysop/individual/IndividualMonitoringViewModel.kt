package com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop.individual

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.agree.ecosystem.monitoring.domain.reqres.model.crudengineparams.CrudEngineParamsPagination
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.SopActivityDetail
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesselIndividual.IndividualSubVessel
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class IndividualMonitoringViewModel(
    private val useCase: MonitoringUseCase
) : DevViewModel() {
    var page = 1
    private val _individualMonitoringIds = DevData<List<IndividualSubVessel>>().apply { default() }
    val individualMonitoringIds = _individualMonitoringIds.immutable()

    private val _loadMoreindividualMonitoringIds =
        DevData<List<IndividualSubVessel>>().apply { default() }
    val loadMoreindividualMonitoringIds = _loadMoreindividualMonitoringIds.immutable()

    private val _entryPoint = DevData<SopActivityDetail>().apply { default() }
    val entryPoint = _entryPoint.immutable()

    fun getIndividualMonitoring(subVesselId: String, userId: String, pageSize: Int = 10) {
        page = 1
        val params = CrudEngineParamsPagination(
            key = "list_subvessel_individual",
            filter = "si.subvessel_id = '$subVesselId' AND s.is_deleted = false AND si.created_by = '$userId'",
            pageSize = pageSize, page++
        )
        useCase.getIndividualSubVessel(params).setHandler(_individualMonitoringIds)
            .let { return@let disposable::add }
    }

    fun loadMoreIndividualMonitoring(subVesselId: String, userId: String) {
        val params = CrudEngineParamsPagination(
            key = "list_subvessel_individual",
            filter = "si.subvessel_id = '$subVesselId' AND s.is_deleted = false AND si.created_by = '$userId'",
            pageSize = 10, page++
        )
        useCase.getIndividualSubVessel(params)
            .setHandler(_loadMoreindividualMonitoringIds)
            .let { return@let disposable::add }
    }

    fun getEntryPoint(subVesselId: String) {
        useCase.getEntryPoint(subVesselId)
            .setHandler(_entryPoint)
            .let { return@let disposable::add }
    }
}
