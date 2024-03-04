package com.agree.ecosystem.monitoring.data.reqres.model.activitysop

import com.agree.ecosystem.core.utils.utility.offline.WebModel
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitySop
import com.google.gson.annotations.SerializedName
import java.util.*

data class ActivitySopItem(
    @field:SerializedName("activities_completed")
    val activitiesCompleted: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("is_completed")
    val isCompleted: Boolean? = null,

    @field:SerializedName("phase_name")
    val phaseName: String? = null,

    @field:SerializedName("repetition")
    val repetition: Int? = null,

    @field:SerializedName("order")
    val order: String? = null,

    @field:SerializedName("detail")
    val detail: String? = null,

    @field:SerializedName("type")
    val type: String? = null
) : WebModel {
    fun toActivitySopEntity(subVesselId: String, isAdditional: Boolean): ActivitySopEntity {
        return ActivitySopEntity(
            activitiesCompleted = activitiesCompleted ?: 0,
            name = name.orEmpty(),
            date = date.orEmpty(),
            description = description.orEmpty(),
            id = id.orEmpty(),
            isCompleted = isCompleted ?: false,
            phaseName = phaseName.orEmpty(),
            repetition = repetition ?: 0,
            order = order.orEmpty(),
            detail = detail.orEmpty(),
            type = type.orEmpty(),
            subVesselId = subVesselId,
            isAdditional = isAdditional
        )
    }

    fun toActivitySop(): ActivitySop {
        return ActivitySop(
            activitiesCompleted = activitiesCompleted ?: 0,
            name = name.orEmpty(),
            date = date.orEmpty(),
            description = description.orEmpty(),
            id = id.orEmpty(),
            isCompleted = isCompleted ?: false,
            phaseName = phaseName.orEmpty(),
            repetition = repetition ?: 0,
            order = order.orEmpty(),
            detail = detail.orEmpty(),
            type = type.orEmpty()
        )
    }
}
