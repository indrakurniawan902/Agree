package com.agree.ecosystem.monitoring.domain.reqres.model.vessel

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Vessel(
    val address: String,
    val code: String,
    val companyId: String,
    val companyMemberId: String,
    val companyMemberWorkerId: String,
    val createdAt: String,
    val createdBy: String,
    val districtId: String,
    val districtName: String,
    val id: String,
    val modifiedAt: String,
    val modifiedBy: String,
    val name: String,
    val provinceId: String,
    val provinceName: String,
    val realizationSize: Float,
    val size: Float,
    val subdistrictId: String,
    val villageId: String,
    val workerId: String,
) : Parcelable
