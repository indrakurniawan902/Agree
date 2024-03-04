package com.agree.ecosystem.monitoring.data.reqres.model.phaseactivity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.agree.ecosystem.core.utils.utility.offline.EntityModel
import com.agree.ecosystem.monitoring.domain.reqres.model.phaseactivity.SubPhaseActivity

@Entity
data class SubPhaseActivityEntity(
    @PrimaryKey(autoGenerate = false)
    val phaseActivityId: String,
    val phaseName: String,
    val description: String,
    val order: String,
    val formSchema: String?,
    val phaseId: String
) : EntityModel {
    fun toSubPhaseActivity(): SubPhaseActivity {
        return SubPhaseActivity(
            phaseId = phaseActivityId,
            phaseName = phaseName,
            description = description,
            order = order,
            formSchema = toObject(formSchema),
        )
    }
}
