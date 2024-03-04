package com.agree.ecosystem.monitoring.domain.reqres.model.incident

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Incident(
    val dateTime: String,
    val subVesselId: String,
    val category: String,
    val population: String,
    val actionTaken: String,
    val status: String,
    val createdAt: String,
    val modifiedAt: String,
    val description: String,
    val activityId: String,
    val phaseId: String,
    val name: String,
    val activityCode: String,
    val expenditure: String,
    val workerName: String,
    val sectorName: String,
    val subSectorName: String,
    val incident: List<String>,
    val data: IncidentData
) : Parcelable

@Keep
@Parcelize
data class IncidentData(
    val images: List<String>
) : Parcelable
