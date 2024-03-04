package com.agree.ecosystem.monitoring.presentation.navigation

import com.agree.ecosystem.monitoring.domain.reqres.model.insertactivitysop.NavToAdditionalActivitySopParams
import com.agree.ecosystem.monitoring.presentation.navigation.params.DetailActivityParams

interface ActivitySopNavigation {
    fun fromActivityInfoToHistory()
    fun fromActivityInfoToInsertAdditionalActivity(navParams: NavToAdditionalActivitySopParams)
    fun fromActivityInfoToDetailActivity(subVesselId: String, navParams: DetailActivityParams)
    fun goToPrevious()
}
