package com.agree.ecosystem.auth.data.reqres.model.verification

import androidx.annotation.Keep

@Keep
data class VerificationBodyPost(
    val phoneNumber: String,
    val otp: String
)
