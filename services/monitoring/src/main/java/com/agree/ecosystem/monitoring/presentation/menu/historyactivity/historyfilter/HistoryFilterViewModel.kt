package com.agree.ecosystem.monitoring.presentation.menu.historyactivity.historyfilter

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitySopGroupByDate
import com.agree.ecosystem.monitoring.utils.Constant
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class HistoryFilterViewModel(
    private val useCase: MonitoringUseCase
) : DevViewModel() {
    var page = 1
    private val _activity = DevData<List<ActivitySopGroupByDate>>().apply { default() }
    val activity = _activity.immutable()

    private val _loadMoreActivity = DevData<List<ActivitySopGroupByDate>>().apply { default() }
    val loadMoreActivity = _loadMoreActivity.immutable()

    fun getListActivity(filter: String, subVesselId: String) {
        page = 1
        if (filter == Constant.FILTER_HISTORY_COMPLETED) {
            useCase.getListHistoryActivityGroupByDate(
                subVesselId, true
            ).setHandler(_activity).let { return@let disposable::add }
        } else {
            useCase.getListHistoryActivityGroupByDate(
                subVesselId, false
            ).setHandler(_activity).let { return@let disposable::add }
        }
    }

    fun loadMoreActivity(filter: String, subVesselId: String) {
        if (filter == Constant.FILTER_HISTORY_COMPLETED) {
            useCase.getListHistoryActivityGroupByDate(
                subVesselId, true
            ).setHandler(_loadMoreActivity).let { return@let disposable::add }
        } else {
            useCase.getListHistoryActivityGroupByDate(
                subVesselId, false
            ).setHandler(_loadMoreActivity).let { return@let disposable::add }
        }
    }
}
