package com.agree.ecosystem.auth.data.reqres.model.forgotAccount

import com.agree.ecosystem.auth.domain.reqres.model.forgotAccount.ForgotAccount
import com.google.gson.annotations.SerializedName

data class ForgotAccountEmailItem(
    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("key")
    val key: String? = null,

    @field:SerializedName("otpNumber")
    val otpNumber: String? = null,

    @field:SerializedName("counter")
    val counter: Int? = 0,

    @field:SerializedName("blokced")
    val blocked: Boolean? = false,

    @field:SerializedName("initTime")
    val initTime: String? = null
) {
    fun toForgotAccount(): ForgotAccount {
        return ForgotAccount(
            userId.orEmpty(),
            key.orEmpty(),
            otpNumber.orEmpty(),
            counter ?: 0,
            blocked ?: false,
            initTime.orEmpty()
        )
    }
}
