package com.agree.ecosystem.monitoring.data.reqres.model.incident

import androidx.annotation.Keep

@Keep
data class IncidentParams(
    val page: Int,
    val quantity: Int,
    val subVesselId: String,
)
