package com.agree.ecosystem.partnership.data.reqres.model.registration

import androidx.annotation.Keep
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.google.gson.annotations.SerializedName

@Keep
data class RegistrationInfoBodyPost(
    val size: Double,
    val subsector: List<SubSector>,
    val type: String,
    val address: String,
    @field:SerializedName("province_id") val provinceId: String,
    val provinceName: String,
    @field:SerializedName("district_id")val districtId: String,
    val districtName: String,
    val unit: String,
    @field:SerializedName("subdistrict_id") val subdistrictId: String,
    val subdistrictName: String,
    @field:SerializedName("village_id") val villageId: String,
    val villageName: String,
    @field:SerializedName("company_id") val companyId: String,
    val companyName: String,
)
