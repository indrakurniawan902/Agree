package com.agree.ecosystem.smartfarming.data.reqres.model

import com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming.RecommendationItem
import com.google.gson.annotations.SerializedName

data class RecommendationItem(

    @field:SerializedName("action_type")
    val actionType: String? = null,

    @field:SerializedName("link")
    val link: String? = null,

    @field:SerializedName("action_label")
    val actionLabel: String? = null,

    @field:SerializedName("content")
    val content: String? = null
){
    fun toRecommendation() : RecommendationItem{
        return RecommendationItem(
            actionType = actionType.orEmpty(),
            link = link.orEmpty(),
            actionLabel = actionLabel.orEmpty(),
            content = content.orEmpty()
        )
    }
}