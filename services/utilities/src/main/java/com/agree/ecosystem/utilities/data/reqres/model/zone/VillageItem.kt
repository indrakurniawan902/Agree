package com.agree.ecosystem.utilities.data.reqres.model.zone

import com.google.gson.annotations.SerializedName

data class VillageItem(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("village_id")
    val villageId: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("subdistrict_id")
    val subdistrictId: String? = null
) {
    fun toVillageEntity(): VillageEntity {
        return VillageEntity(
            id.orEmpty(),
            villageId.orEmpty(),
            name.orEmpty(),
            subdistrictId.orEmpty()
        )
    }
}
