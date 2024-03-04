package com.agree.ecosystem.monitoring.presentation.menu.phaseactivity.subphaseactivity

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.agree.ecosystem.monitoring.domain.reqres.model.phaseactivity.PhaseActivity
import com.agree.ecosystem.monitoring.domain.reqres.model.phaseactivity.SubPhaseActivity
import com.devbase.data.source.DevData
import com.devbase.data.source.VmData
import com.devbase.data.utilities.DevViewModel

class SubPhaseActivityViewModel(
    private val useCase: MonitoringUseCase
) : DevViewModel() {
    private val _subPhaseActivity = DevData<List<SubPhaseActivity>>().apply { default() }

    val subPhaseActivity = _subPhaseActivity.immutable()

    fun getActivityType(data: PhaseActivity) {
        _subPhaseActivity.value = VmData.success(data.subPhaseActivityItems)
    }
}
