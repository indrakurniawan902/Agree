package com.agree.ecosystem.monitoring.presentation.navigation

import androidx.navigation.NavController
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesselIndividual.IndividualSubVessel
import com.agree.ui.data.reqres.model.FormItem

interface DetailActivitySopNavigation {
    fun getNavController(): NavController?
    fun goToPrevious()
    fun fromDetailActivitySopToIndividualMonitoring(
        subVesselId: String,
        type: String,
        activityName: String,
        guideContent: String,
        date: String
    )

    fun fromIndividualMonitoringToDetailActivitySop(individualSubVessel: IndividualSubVessel? = null)
    fun fromDetailActivitySopToActivitySummary(activityName: String, formItems: List<FormItem>)
}
