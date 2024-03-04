package com.agree.ecosystem.monitoring.domain.reqres.model.insertactivitysop

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class InsertActivitySopBundleParams(
    val commodityId: String,
    val commodityVarietyId: String,
    val companyId: String,
    val companyMemberId: String,
    val vesselId: String,
    val workerId: String,
    val subVesselId: String,
    val phaseActivityId: String
) : Parcelable
