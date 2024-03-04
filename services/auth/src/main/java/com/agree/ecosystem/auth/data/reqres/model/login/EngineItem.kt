package com.agree.ecosystem.auth.data.reqres.model.login

import com.agree.ecosystem.auth.domain.reqres.model.login.Engine
import com.google.gson.annotations.SerializedName

data class EngineItem(
    @field:SerializedName("token_type")
    val tokenType: String? = null,

    @field:SerializedName("duration")
    val duration: Int? = null,

    @field:SerializedName("access_token")
    val accessToken: String? = null,
) {
    fun toEngine(): Engine {
        return Engine(accessToken.orEmpty())
    }
}
