package com.agree.ecosystem.partnership.data.reqres.model.unittype

import com.agree.ecosystem.partnership.domain.reqres.model.unittype.UnitType
import com.google.gson.annotations.SerializedName

data class UnitTypeItem(
    @field:SerializedName("key")
    val key: String,
    @field:SerializedName("utility_type")
    val utilityType: String,
    @field:SerializedName("value")
    val value: String,
    @field:SerializedName("name")
    val name: String
) {
    fun toUnitType(): UnitType {
        return UnitType(
            key = key,
            utilityType = utilityType,
            value = value,
            name = name
        )
    }
}
