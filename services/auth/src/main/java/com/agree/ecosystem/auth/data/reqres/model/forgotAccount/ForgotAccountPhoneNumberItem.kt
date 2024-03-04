package com.agree.ecosystem.auth.data.reqres.model.forgotAccount

import com.agree.ecosystem.auth.domain.reqres.model.forgotAccount.ForgotAccountPhoneNumber
import com.google.gson.annotations.SerializedName

data class ForgotAccountPhoneNumberItem(
    @field:SerializedName("otpSent")
    val otpSent: String,

    @field:SerializedName("phoneNumber")
    val phoneNumber: String

) {
    fun toForgotAccount(): ForgotAccountPhoneNumber {
        return ForgotAccountPhoneNumber(
            otpSent,
            phoneNumber
        )
    }
}
