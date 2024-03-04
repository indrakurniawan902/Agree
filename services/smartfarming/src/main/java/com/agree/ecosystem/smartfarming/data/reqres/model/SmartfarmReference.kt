package com.agree.ecosystem.smartfarming.data.reqres.model

import com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming.SmartfarmReference
import com.google.gson.annotations.SerializedName

data class SmartfarmReference(

    @field:SerializedName("reference")
    val reference: List<ReferenceItem>? = mutableListOf(),

    @field:SerializedName("key")
    val key: String? = null
){
    fun toSmartfarmRef(): SmartfarmReference{
        return SmartfarmReference(
            reference = reference?.map { it.toReferenceItem() }.orEmpty(),
            key = key.orEmpty()
        )
    }
}