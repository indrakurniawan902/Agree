package com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RecommendationItem(

    @field:SerializedName("action_type")
    val actionType: String? = null,

    @field:SerializedName("link")
    val link: String? = null,

    @field:SerializedName("")
    val actionLabel: String? = null,

    @field:SerializedName("content")
    val content: String? = null
): Parcelable