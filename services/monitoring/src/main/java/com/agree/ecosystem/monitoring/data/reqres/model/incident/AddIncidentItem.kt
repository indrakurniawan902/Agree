package com.agree.ecosystem.monitoring.data.reqres.model.incident

import androidx.annotation.Keep
import com.agree.ecosystem.monitoring.domain.reqres.model.incident.AddIncident
import com.agree.ecosystem.monitoring.domain.reqres.model.incident.ImageData
import com.google.gson.annotations.SerializedName

@Keep
data class AddIncidentItem(
    @field:SerializedName("activity_id")
    val activityId: String,
    @field:SerializedName("phase_id")
    val phaseId: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("date_time")
    val dateTime: String? = "",
    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("category")
    val category: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("activity_code")
    val activityCode: String,
    @field:SerializedName("expenditure")
    val expenditure: String,
    @field:SerializedName("subvessel_id")
    val subvesselId: String,
    @field:SerializedName("population")
    val population: String,
    @field:SerializedName("data")
    val data: ImageDataItem? = null
) {
    fun toAddIncident(): AddIncident {
        return AddIncident(
            activityId = activityId.orEmpty(),
            phaseId = phaseId.orEmpty(),
            name = name.orEmpty(),
            dateTime = dateTime.orEmpty(),
            status = status.orEmpty(),
            category = category.orEmpty(),
            description = description.orEmpty(),
            activityCode = activityCode.orEmpty(),
            expenditure = expenditure.orEmpty(),
            subvesselId = subvesselId.orEmpty(),
            population = population.orEmpty(),
            data = ImageData(
                images = data?.images?.map {
                    it
                }.orEmpty()
            ),
        )
    }
}

data class ImageDataItem(
    @field:SerializedName("images")
    val images: List<String>
)
