package com.agree.ecosystem.monitoring.data.reqres.model.phaseactivity

import com.agree.ecosystem.core.utils.utility.offline.WebModel
import com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop.FormSchema
import com.agree.ecosystem.monitoring.domain.reqres.model.phaseactivity.PhaseActivity
import com.agree.ecosystem.monitoring.domain.reqres.model.phaseactivity.SubPhaseActivity
import com.google.gson.annotations.SerializedName

data class PhaseActivityItem(
    @field:SerializedName("phase_id")
    val phaseId: String? = null,

    @field:SerializedName("phase_name")
    val phaseName: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("activities")
    val subPhaseActivityItem: List<SubPhaseActivityItem>? = null

) : WebModel {
    fun toPhaseActivity(): PhaseActivity {
        return PhaseActivity(
            phaseId = phaseId.orEmpty(),
            phaseName = phaseName.orEmpty(),
            description = description.orEmpty(),
            subPhaseActivityItems = subPhaseActivityItem?.map { it.toSubPhaseActivity() }
                ?: emptyList()
        )
    }

    fun toPhaseActivityEntity(subVesselId: String): PhaseActivityEntity {
        return PhaseActivityEntity(
            phaseId = phaseId.orEmpty(),
            phaseName = phaseName.orEmpty(),
            description = description.orEmpty(),
            subVesselId = subVesselId
        )
    }
}

data class SubPhaseActivityItem(
    @SerializedName("phase_activity_id")
    val phaseActivityId: String? = null,

    @SerializedName("activity_name")
    val phaseName: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("order")
    val order: String? = null,

    @SerializedName("data")
    val formSchema: FormSchema? = null
) : WebModel {
    fun toSubPhaseActivity(): SubPhaseActivity {
        return SubPhaseActivity(
            phaseId = phaseActivityId.orEmpty(),
            phaseName = phaseName.orEmpty(),
            description = description.orEmpty(),
            order = order ?: "1",
            formSchema = formSchema
        )
    }

    fun toSubPhaseActivityEntity(phaseId: String): SubPhaseActivityEntity {
        return SubPhaseActivityEntity(
            phaseActivityId = phaseActivityId.orEmpty(),
            phaseName = phaseName.orEmpty(),
            description = description.orEmpty(),
            order = order ?: "1",
            formSchema = toJson(formSchema),
            phaseId = phaseId
        )
    }
}
