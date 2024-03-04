package com.agree.ecosystem.smartfarming.data.reqres.model

import com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming.ParameterTestFormSchema
import com.google.gson.annotations.SerializedName
import java.util.Objects

data class ParameterTestFormSchemaItem (

    @field:SerializedName("icon")
    val icon: String,

    @field:SerializedName("label")
    val label: String,

    @field:SerializedName("key")
    val key: String,

    @field:SerializedName("raw_value")
    val raw_value: String,

    @field:SerializedName("smartfarm_reference")
    val smartfarm_reference: SmartfarmReference?,

    @field:SerializedName("value")
    val value: String,

    @field:SerializedName("is_read")
    val isRead: Boolean,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("status_recommendation")
    val status_recommendation: List<RecommendationItem>? = mutableListOf(),
    ) {
    fun toParameterTestFormSchema() : ParameterTestFormSchema {
        return ParameterTestFormSchema(
            icon = icon.orEmpty(),
            label = label.orEmpty(),
            value = value.orEmpty(),
            isRead = isRead,
            status = when (status) {
                "good" -> ParameterTestFormSchema.Status.GOOD
                "wary" -> ParameterTestFormSchema.Status.WARY
                else -> ParameterTestFormSchema.Status.BAD
            },
            key = key.orEmpty(),
            raw_value = raw_value.orEmpty(),
            smartfarm_reference = smartfarm_reference?.toSmartfarmRef(),
            status_recommendation = status_recommendation?.map { it.toRecommendation() }
        )
    }
}