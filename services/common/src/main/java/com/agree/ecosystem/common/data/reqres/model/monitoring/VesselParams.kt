package com.agree.ecosystem.common.data.reqres.model.monitoring

import androidx.annotation.Keep

@Keep
data class VesselParams(
    val page: Int,
    val quantity: Int,
    val vesselName: String,
    val userId: String,
    val status: String = "active",
    val subSectorId: String,
)
