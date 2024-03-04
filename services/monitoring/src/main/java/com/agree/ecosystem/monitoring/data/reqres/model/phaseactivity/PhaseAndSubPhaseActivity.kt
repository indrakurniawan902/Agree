package com.agree.ecosystem.monitoring.data.reqres.model.phaseactivity

import androidx.room.Embedded
import androidx.room.Relation
import com.agree.ecosystem.monitoring.domain.reqres.model.phaseactivity.PhaseActivity

data class PhaseAndSubPhaseActivity(
    @Embedded
    val phaseActivity: PhaseActivityEntity,

    @Relation(
        parentColumn = "phaseId",
        entityColumn = "phaseId"
    )
    val subPhaseActivity: List<SubPhaseActivityEntity>
) {
    fun toPhaseActivity(): PhaseActivity {
        return PhaseActivity(
            phaseId = phaseActivity.phaseId,
            phaseName = phaseActivity.phaseName,
            description = phaseActivity.description,
            subPhaseActivityItems = subPhaseActivity.map { it.toSubPhaseActivity() }
        )
    }
}
