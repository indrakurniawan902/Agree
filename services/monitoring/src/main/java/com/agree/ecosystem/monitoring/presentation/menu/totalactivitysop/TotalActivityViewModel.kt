package com.agree.ecosystem.monitoring.presentation.menu.totalactivitysop

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.agree.ecosystem.monitoring.domain.reqres.model.totalactivitysop.TotalActivitySop
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class TotalActivityViewModel(
    private val useCase: MonitoringUseCase
) : DevViewModel() {
    private val _totalActivitySop = DevData<TotalActivitySop>().apply { default() }
    val totalActivity = _totalActivitySop.immutable()

    fun getTotalActivitySop(subVesselId: String, block: (Int.(String?) -> Unit)) {
        useCase.getTotalActivity(subVesselId)
            .setHandler(_totalActivitySop, block)
            .let { return@let disposable::add }
    }
}
