package com.agree.ecosystem.monitoring.domain.reqres.model.activitysop

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ActivitySop(
    val activitiesCompleted: Int,
    val name: String,
    val date: String,
    val description: String,
    val id: String,
    val isCompleted: Boolean,
    val phaseName: String,
    val repetition: Int,
    val order: String,
    val detail: String,
    val type: String,
    var isAdditional: Boolean = false
) : Parcelable {
    fun getDetailActivity(): String = "$name ($activitiesCompleted/$repetition)"
    fun getDetailActivityWithoutFrequency(): String = name
}
