package com.agree.ecosystem.utilities.data.reqres.model.zone

import com.google.gson.annotations.SerializedName

data class DistrictItem(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("district_id")
    val districtId: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("province_id")
    val provinceId: String? = null
) {
    fun toDistrictEntity(): DistrictEntity {
        return DistrictEntity(
            id.orEmpty(),
            districtId.orEmpty(),
            name.orEmpty(),
            provinceId.orEmpty()
        )
    }
}
