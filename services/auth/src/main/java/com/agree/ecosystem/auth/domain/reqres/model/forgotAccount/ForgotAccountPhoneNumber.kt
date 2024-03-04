package com.agree.ecosystem.auth.domain.reqres.model.forgotAccount

data class ForgotAccountPhoneNumber(
    val otpSent: String,
    val phoneNumber: String
)
