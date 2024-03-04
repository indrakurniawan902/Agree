package com.agree.ecosystem.monitoring.domain.reqres.model.incidentcomment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IncidentComment(
    val id: String,
    val activityId: String,
    val commenterId: String,
    val commenterName: String,
    val createdAt: String,
    val message: String,
    val commenterType: String,
    val images: List<String>
) : Parcelable
