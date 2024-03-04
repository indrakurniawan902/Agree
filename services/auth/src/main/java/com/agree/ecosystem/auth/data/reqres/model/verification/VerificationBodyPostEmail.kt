package com.agree.ecosystem.auth.data.reqres.model.verification

import androidx.annotation.Keep

@Keep
data class VerificationBodyPostEmail(
    val email: String,
    val otp: String
)
