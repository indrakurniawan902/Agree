package com.agree.ecosystem.monitoring.domain.reqres.model.phaseactivity

import android.os.Parcelable
import androidx.annotation.Keep
import com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop.FormSchema
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class PhaseActivity(
    val phaseId: String,
    val phaseName: String,
    val description: String,
    val subPhaseActivityItems: List<SubPhaseActivity>
) : Parcelable

@Keep
@Parcelize
data class SubPhaseActivity(
    val phaseId: String,
    val phaseName: String,
    val description: String,
    val order: String,
    val formSchema: FormSchema?
) : Parcelable
