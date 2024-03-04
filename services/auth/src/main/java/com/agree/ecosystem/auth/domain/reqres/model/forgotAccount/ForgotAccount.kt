package com.agree.ecosystem.auth.domain.reqres.model.forgotAccount

data class ForgotAccount(
    val userId: String,
    val key: String,
    val otpNumber: String,
    val counter: Int,
    val blocked: Boolean,
    val initTime: String
)
