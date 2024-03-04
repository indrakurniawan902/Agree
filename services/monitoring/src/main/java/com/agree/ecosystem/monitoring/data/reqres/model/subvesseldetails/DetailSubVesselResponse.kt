package com.agree.ecosystem.monitoring.data.reqres.model.subvesseldetails

data class DetailSubVesselResponse(
    val code: Int,
    val `data`: List<DetailSubVesselItem>,
    val message: String,
    val meta: Meta,
    val success: Boolean
)
