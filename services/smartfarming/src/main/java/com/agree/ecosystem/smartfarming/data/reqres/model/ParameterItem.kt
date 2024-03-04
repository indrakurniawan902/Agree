package com.agree.ecosystem.smartfarming.data.reqres.model

import com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming.ParameterItem
import com.google.gson.annotations.SerializedName

data class ParameterItem(

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("value")
    val value: String? = null,

    @field:SerializedName("suffix")
    val suffix: String? = null,
){
    fun toParameter(): ParameterItem{
        return ParameterItem(
            type = type.orEmpty(),
            value = value.orEmpty(),
            suffix = suffix.orEmpty()
        )
    }
}