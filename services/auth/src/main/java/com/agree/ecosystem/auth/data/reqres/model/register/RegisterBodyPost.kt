package com.agree.ecosystem.auth.data.reqres.model.register

import androidx.annotation.Keep

@Keep
data class RegisterBodyPost(
    val name: String,
    val username: String,
    val phoneNumber: String,
    val email: String?,
    val password: String,
    val confirmPassword: String
)
