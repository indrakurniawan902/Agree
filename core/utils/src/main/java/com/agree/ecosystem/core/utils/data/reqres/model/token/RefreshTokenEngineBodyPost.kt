package com.agree.ecosystem.core.utils.data.reqres.model.token

import androidx.annotation.Keep

@Keep
data class RefreshTokenEngineBodyPost(
    val username: String,
    val password: String,
    val duration: Int
)
