package com.agree.ecosystem.monitoring.data.reqres.model.totalactivitysop

import com.agree.ecosystem.monitoring.domain.reqres.model.totalactivitysop.TotalActivitySop
import com.google.gson.annotations.SerializedName

data class TotalActivitySopItem(
    @field:SerializedName("total_activities")
    val totalActivities: Int? = null,

    @field:SerializedName("activities_completed")
    val activitiesCompleted: Int? = null,

    @field:SerializedName("activities_missed")
    val activitiesMissed: Int? = null
) {
    fun toTotalActivity(): TotalActivitySop {
        return TotalActivitySop(
            totalActivities = totalActivities ?: 0,
            activitiesCompleted = activitiesCompleted ?: 0,
            activitiesMissed = activitiesMissed ?: 0
        )
    }
}
