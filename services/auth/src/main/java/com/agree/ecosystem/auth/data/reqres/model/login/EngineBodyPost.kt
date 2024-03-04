package com.agree.ecosystem.auth.data.reqres.model.login

import androidx.annotation.Keep

@Keep
data class EngineBodyPost(
    val username: String,
    val password: String,
    val duration: Int
)
