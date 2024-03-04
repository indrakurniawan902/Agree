package com.agree.ecosystem.monitoring.data.reqres.model.insertactivitysop

import com.agree.ecosystem.monitoring.domain.reqres.model.subvesselIndividual.IndividualSubVessel
import com.google.gson.annotations.SerializedName

data class UpdateActivitySopBodyPost(
    @SerializedName("data")
    val `data`: String,
    @SerializedName("update")
    val update: List<IndividualSubVessel>? = null,
    @SerializedName("insert")
    val insert: List<IndividualSubVessel>? = null,
    @SerializedName("delete")
    val delete: List<IndividualSubVessel>? = null,
    @SerializedName("phase_activity_id")
    val phaseActivityId: String?
)
