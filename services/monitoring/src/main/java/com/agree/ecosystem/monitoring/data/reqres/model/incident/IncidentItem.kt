package com.agree.ecosystem.monitoring.data.reqres.model.incident

import androidx.annotation.Keep
import com.agree.ecosystem.monitoring.domain.reqres.model.incident.Incident
import com.agree.ecosystem.monitoring.domain.reqres.model.incident.IncidentData
import com.google.gson.annotations.SerializedName

@Keep
data class IncidentItem(

    @field:SerializedName("id")
    val activityId: String? = null,

    @field:SerializedName("phase_id")
    val phaseId: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("date_time")
    val dateTime: String? = null,

    @field:SerializedName("subvessel_id")
    val subVesselId: String? = null,

    @field:SerializedName("category")
    val category: String? = null,

    @field:SerializedName("population")
    val population: String? = null,

    @field:SerializedName("action_taken")
    val actionTaken: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("activity_code")
    val activityCode: String? = null,

    @field:SerializedName("expenditure")
    val expenditure: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("modified_at")
    val modifiedAt: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("worker_name")
    val workerName: String? = null,

    @field:SerializedName("sector_name")
    val sectorName: String? = null,

    @field:SerializedName("subsector_name")
    val subSectorName: String? = null,

    @field:SerializedName("incident")
    val incident: List<String>? = null,

    @field:SerializedName("data")
    val data: IncidentDataItem? = null,
) {
    fun toIncident(): Incident {
        return Incident(
            activityId = activityId.orEmpty(),
            phaseId = phaseId.orEmpty(),
            name = name.orEmpty(),
            activityCode = activityCode.orEmpty(),
            expenditure = expenditure.orEmpty(),
            workerName = workerName.orEmpty(),
            sectorName = sectorName.orEmpty(),
            subSectorName = subSectorName.orEmpty(),
            dateTime = dateTime.orEmpty(),
            subVesselId = subVesselId.orEmpty(),
            category = category.orEmpty(),
            population = population.orEmpty(),
            actionTaken = actionTaken.orEmpty(),
            status = status.orEmpty(),
            createdAt = createdAt.orEmpty(),
            modifiedAt = modifiedAt.orEmpty(),
            description = description.orEmpty(),
            incident = incident?.map { it }.orEmpty(),
            data = data?.toIncidentData() ?: IncidentData(images = listOf())
        )
    }
}

@Keep
data class IncidentDataItem(
    @field:SerializedName("images")
    val images: List<String>? = null
) {
    fun toIncidentData(): IncidentData {
        return IncidentData(
            images = images?.map { it }.orEmpty()
        )
    }
}
