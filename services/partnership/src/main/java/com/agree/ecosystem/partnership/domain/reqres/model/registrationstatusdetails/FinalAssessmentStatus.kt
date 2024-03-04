package com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails

import android.os.Parcelable
import androidx.annotation.Keep
import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class FinalAssessmentStatus(
    val address: String,
    val createdAt: String,
    val createdBy: String,
    val description: String,
    val districtId: String,
    val id: String,
    val location: String,
    val midLat: String,
    val midLong: String,
    val provinceId: String,
    val realizationSize: Double,
    val size: Double,
    val status: Boolean,
    val subVessels: List<SubVessel>,
    val subDistrictId: String,
    val subSectorId: String,
    val subVesselCount: Int,
    val userId: String,
    val vesselName: String,
    val villageId: String,
    val workerId: String
) : Parcelable
