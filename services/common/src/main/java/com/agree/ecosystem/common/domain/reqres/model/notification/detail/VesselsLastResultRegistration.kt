package com.agree.ecosystem.common.domain.reqres.model.notification.detail

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class VesselsLastResultRegistration(
    val vesselName: String,
    val userId: String,
    val workerId: String,
    val size: Double,
    val status: String,
    val address: String,
    val description: String,
    val provinceId: String,
    val disctrictId: String,
    val subDistrictId: String,
    val villageId: String,
    val realizationSize: Double,
    val cratedAt: String,
    val cratedBy: String,
    val location: String,
    val subVesselCount: Int,
    val subVessel: List<SubVesselsLastResultRegistration>
) : Parcelable

@Keep
@Parcelize
data class SubVesselsLastResultRegistration(
    val id: String,
    val subvesselName: String,
    val submissionId: String,
    val submissionCessselId: String,
    val workerId: String,
    val subSectorId: String,
    val size: Double,
    val createdAt: String,
    val data: DataSubVessle,
    val commodityId: String,
    val commodityName: String,
    val workerName: String,
    val sectorName: String,
    val status: String,
    val description: String,
    val dynamicView: String
) : Parcelable

@Keep
@Parcelize
data class DataSubVessle(
    val distanceToSettleMent: Int,
    val floor: Int,
    val fount: String,
    val funding: String,
    val humidity: String,
    val legality: String,
    val rainfall: String,
    val sunshine: String,
    val surveyNote: String,
    val temperature: Int,
    val typeOfSubarea: String,
    val windVelocity: Int
) : Parcelable
