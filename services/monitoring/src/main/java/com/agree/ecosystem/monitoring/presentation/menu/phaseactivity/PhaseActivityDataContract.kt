package com.agree.ecosystem.monitoring.presentation.menu.phaseactivity

import com.agree.ecosystem.monitoring.domain.reqres.model.phaseactivity.PhaseActivity

interface PhaseActivityDataContract {

    fun onGetPhaseActivitySuccess(data: List<PhaseActivity>)

    fun onGetPhaseActivityLoading()

    fun onGetPhaseActivityEmpty()
}
