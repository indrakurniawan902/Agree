package com.agree.ecosystem.smartfarming.data.reqres.model

import com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming.ParameterTestFormSchema
import com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming.ReferenceItem
import com.google.gson.annotations.SerializedName

data class ReferenceItem(

    @field:SerializedName("parameter")
    val parameter: List<ParameterItem>? = mutableListOf(),

    @field:SerializedName("recommendation")
    val recommendation: List<RecommendationItem>? = mutableListOf(),

    @field:SerializedName("status")
    val status: String? = null
){
    fun toReferenceItem():ReferenceItem{
        return ReferenceItem(
            parameter = parameter?.map { it.toParameter() },
            recommendation = recommendation?.map { it.toRecommendation() },
            status = when (status) {
                "good" -> ReferenceItem.Status.GOOD
                "wary" -> ReferenceItem.Status.WARY
                else -> ReferenceItem.Status.BAD
            },
        )
    }
}