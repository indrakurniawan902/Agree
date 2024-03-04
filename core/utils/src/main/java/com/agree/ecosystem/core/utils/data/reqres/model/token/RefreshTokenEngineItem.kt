package com.agree.ecosystem.core.utils.data.reqres.model.token

import com.google.gson.annotations.SerializedName

data class RefreshTokenEngineItem(

    @field:SerializedName("token_type")
    val tokenType: String? = null,

    @field:SerializedName("duration")
    val duration: Int? = null,

    @field:SerializedName("access_token")
    val accessToken: String? = null,
)
