package com.agree.ecosystem.partnership.data.reqres.model.registration

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RegistrationBodyPost(
    val size: Double,
    val subsector: List<SubsectorBodyPost>,
    val type: String,
    val address: String,
    @field:SerializedName("province_id") val provinceId: String,
    @field:SerializedName("district_id") val districtId: String,
    @field:SerializedName("subdistrict_id") val subDistrictId: String,
    @field:SerializedName("village_id") val villageId: String,
    @field:SerializedName("company_id") val companyId: String,
    val commodity: List<CommodityBodyPost>
)

data class SubsectorBodyPost(
    val id: String
)

data class CommodityBodyPost(
    val id: String
)
