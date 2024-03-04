package com.agree.ecosystem.core.utils.domain.reqres.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: Any? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("code_name")
    val codeName: String? = null
)
