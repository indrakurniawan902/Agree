package com.agree.ecosystem.auth.data.reqres.model.login

import androidx.annotation.Keep

@Keep
data class LoginBodyPost(
    val username: String,
    val password: String,
    val firebaseToken: String
)
