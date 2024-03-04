package com.agree.ecosystem.monitoring.data.reqres.model.activitysop

import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitiesMissed
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitiesSopMissed
import com.google.gson.annotations.SerializedName

data class ActivitiesSopMissedItem(

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("activities")
    val activities: List<ActivitiesItem>? = null
) {
    fun toActivitiesSopMissed(): ActivitiesSopMissed {
        return ActivitiesSopMissed(
            date = date.orEmpty(),
            activities = activities?.map { it.toActivitiesMissed() }.orEmpty()
        )
    }
}

data class ActivitiesItem(

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("activities_completed")
    val activitiesCompleted: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("detail")
    val detail: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("phase_name")
    val phaseName: String? = null,

    @field:SerializedName("repetition")
    val repetition: Int? = null,

    @field:SerializedName("is_completed")
    val isCompleted: Boolean? = null,

    @field:SerializedName("type")
    val type: String? = null
) {
    fun toActivitiesMissed(): ActivitiesMissed {
        return ActivitiesMissed(
            date = date.orEmpty(),
            activitiesCompleted = activitiesCompleted ?: 0,
            name = name.orEmpty(),
            description = description.orEmpty(),
            detail = detail.orEmpty(),
            id = id.orEmpty(),
            phaseName = phaseName.orEmpty(),
            repetition = repetition ?: 0,
            isCompleted = isCompleted ?: false,
            type = type.orEmpty()
        )
    }
}
