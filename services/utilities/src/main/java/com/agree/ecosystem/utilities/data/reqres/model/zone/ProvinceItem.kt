package com.agree.ecosystem.utilities.data.reqres.model.zone

import com.google.gson.annotations.SerializedName

data class ProvinceItem(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("province_id")
    val provinceId: String? = null,

    @field:SerializedName("name")
    val name: String? = null
) {
    fun toProviceEntity(): ProvinceEntity {
        return ProvinceEntity(
            id.orEmpty(),
            provinceId.orEmpty(),
            name.orEmpty()
        )
    }
}
