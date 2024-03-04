package com.agree.ecosystem.monitoring.presentation.navigation

import androidx.navigation.NavController
import com.agree.ecosystem.monitoring.domain.reqres.model.incident.Incident
import com.agree.ecosystem.monitoring.domain.reqres.model.insertactivitysop.NavToAdditionalActivitySopParams
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVessel
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.Transaction
import com.agree.ecosystem.monitoring.presentation.navigation.params.DetailActivityParams

interface DetailSubVesselNavigation {
    fun getNavController(): NavController?
    fun goToPrevious()
    fun fromDetailSubVesselToActivitySop(subVesselId: String, detailSubVessel: DetailSubVessel)
    fun fromDetailSubVesselToInsertAdditionalActivity(navParams: NavToAdditionalActivitySopParams)
    fun fromDetailSubVesselToDetailActivity(subVesselId: String, navParams: DetailActivityParams)
    fun fromDetailSubVesselToAddIncident()
    fun fromDetailSubVesselToIncidentReport(incident: Incident?)
    fun fromDetailSubVesselToTransactionDetail(transaction: Transaction?)
    fun fromDetailSubVesselToInsertTransaction(transactionId: String?)
    fun fromDetailSubVesselToSmartFarming(detailSubVessel: DetailSubVessel)
    fun fromIncidentReportToIncidentDetail(incident: Incident?)
    fun fromTransactionDetailToUpdateTransaction(transactionId: String?)
}
