package com.agree.ecosystem.monitoring.presentation.menu.historyactivity.historyfilter

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitiesSopMissed
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class HistoryMissedFilterViewModel(
    private val useCase: MonitoringUseCase
) : DevViewModel() {
    var page = 1
    private val _activity = DevData<List<ActivitiesSopMissed>>().apply { default() }
    val activity = _activity.immutable()

    private val _loadMoreActivity = DevData<List<ActivitiesSopMissed>>().apply { default() }
    val loadMoreActivity = _loadMoreActivity.immutable()

    fun getListActivity(subVesselId: String) {
        page = 1
        useCase.getListHistoryMissedActivityGroupByDate(subVesselId, false)
            .setHandler(_activity).let { return@let disposable::add }
    }

    fun loadMoreActivity(subVesselId: String) {
        useCase.getListHistoryMissedActivityGroupByDate(subVesselId, false)
            .setHandler(_loadMoreActivity).let { return@let disposable::add }
    }
}
