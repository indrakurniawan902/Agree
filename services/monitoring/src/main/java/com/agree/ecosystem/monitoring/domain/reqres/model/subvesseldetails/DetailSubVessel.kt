package com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails
import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DetailSubVessel(
    val activityName: String,
    val bruto: String,
    val code: String,
    val commodityId: String,
    val commodityName: String,
    val commodityVarietyId: String,
    val companyId: String,
    val companyMemberId: String,
    val companySubMemberId: String,
    val createdAt: String,
    val createdBy: String,
    val cycleCount: Int,
    val datePlanted: String,
    val districtId: String,
    val districtName: String,
    val id: String,
    val isDeleted: Boolean,
    val modifiedAt: String,
    val modifiedBy: String,
    val netto: String,
    val order: String,
    val population: String,
    val size: String,
    val sopId: String,
    val status: String,
    val subSectorId: String,
    val subVesselName: String,
    val target: String,
    val varietyName: String,
    val vesselId: String,
    val workerId: String,
    val workerName: String,
    val recordType: String,
    val hasSmartfarm: Boolean,
) : Parcelable
