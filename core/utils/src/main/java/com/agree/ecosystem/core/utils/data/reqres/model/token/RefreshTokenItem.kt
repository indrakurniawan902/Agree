package com.agree.ecosystem.core.utils.data.reqres.model.token

import com.google.gson.annotations.SerializedName

data class RefreshTokenItem(

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("accessToken")
    val accessToken: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("refreshToken")
    val refreshToken: String? = null
)
