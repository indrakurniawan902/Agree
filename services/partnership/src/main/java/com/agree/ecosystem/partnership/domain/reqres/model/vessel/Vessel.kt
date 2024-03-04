package com.agree.ecosystem.partnership.domain.reqres.model.vessel

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Vessel(
    val id: String,
    val vesselId: String,
    val companyMemberId: String,
    val companyMemberWorkerId: String,
    val workerId: String,
    val address: String,
    val provinceId: String,
    val provinceName: String,
    val districtId: String,
    val subDistrictId: String,
    val villageId: String,
    val name: String,
    val realizationSize: Double,
    val size: Double,
    val createdAt: String,
    val createdBy: String,
    val modifiedAt: String,
    val modifiedBy: String,
    val code: String
) : Parcelable
