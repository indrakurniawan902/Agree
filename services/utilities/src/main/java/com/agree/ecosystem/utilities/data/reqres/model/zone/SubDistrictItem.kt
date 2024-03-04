package com.agree.ecosystem.utilities.data.reqres.model.zone

import com.google.gson.annotations.SerializedName

data class SubDistrictItem(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("subdistrict_id")
    val subdistrictId: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("district_id")
    val districtId: String? = null
) {
    fun toSubDistrictEntity(): SubDistrictEntity {
        return SubDistrictEntity(
            id.orEmpty(),
            subdistrictId.orEmpty(),
            name.orEmpty(),
            districtId.orEmpty()
        )
    }
}
