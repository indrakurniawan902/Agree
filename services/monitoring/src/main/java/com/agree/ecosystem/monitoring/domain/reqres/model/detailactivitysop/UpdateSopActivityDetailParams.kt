package com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop

import com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop.SopActivityDetailBodyPost
import com.google.gson.annotations.SerializedName

data class UpdateSopActivityDetailParams(
    val id: String,
    @SerializedName("field_id")
    val fieldId: String = "id",
    val data: SopActivityDetailBodyPost
)
