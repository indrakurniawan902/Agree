package com.agree.ecosystem.monitoring.data.reqres.model.activitysop

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.agree.ecosystem.core.utils.utility.offline.EntityModel
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitySop

@Entity
data class ActivitySopEntity(
    val activitiesCompleted: Int,
    val name: String,
    val date: String,
    val description: String,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val isCompleted: Boolean,
    val phaseName: String,
    val repetition: Int,
    val order: String,
    val detail: String,
    val type: String,
    val subVesselId: String,
    val isAdditional: Boolean
) : EntityModel {
    fun toActivitySop(): ActivitySop {
        return ActivitySop(
            activitiesCompleted = activitiesCompleted,
            name = name,
            date = date,
            description = description,
            id = id,
            isCompleted = isCompleted,
            phaseName = phaseName,
            repetition = repetition,
            order = order,
            detail = detail,
            type = type
        )
    }
}
