package com.agree.ecosystem.smartfarming.domain.reqres.model.detailsubvessel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailSubVesselParams(
    val id: String,
    val subSectorId: String,
    val subVesselName: String,
    val workerName: String
): Parcelable