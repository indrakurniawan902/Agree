package com.agree.ecosystem.monitoring.data.reqres.model.subvesseldetails

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class InsertSopDateBodyPost(
    @field:SerializedName("subvessel_id")
    val subvesselId: String,
    val date: String
)
