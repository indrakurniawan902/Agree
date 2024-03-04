package com.agree.ecosystem.monitoring.presentation.menu.phaseactivity.subphaseactivity

import com.agree.ecosystem.monitoring.domain.reqres.model.phaseactivity.SubPhaseActivity

interface SubPhaseActivityDataContract {
    fun onGetSuccess(data: List<SubPhaseActivity>)
}
