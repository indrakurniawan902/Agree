package com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop

import com.google.gson.annotations.SerializedName

data class SopActivityDetailBodyPost(
    val data: String,
    @SerializedName("date_time")
    val dateTime: String,
    @SerializedName("modified_at")
    val modifiedAt: String,
    @SerializedName("modified_by")
    val modifiedBy: String
)
