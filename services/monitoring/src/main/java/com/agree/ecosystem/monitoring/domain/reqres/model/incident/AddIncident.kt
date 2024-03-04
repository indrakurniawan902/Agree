package com.agree.ecosystem.monitoring.domain.reqres.model.incident

data class AddIncident(
    val activityId: String,
    val phaseId: String,
    val name: String,
    val dateTime: String,
    val status: String,
    val category: String,
    val description: String,
    val activityCode: String,
    val expenditure: String,
    val subvesselId: String,
    val population: String,
    val data: ImageData
)

data class ImageData(
    val images: List<String>
)
