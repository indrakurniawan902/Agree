package com.agree.ecosystem.auth.data.reqres.model.login

import com.agree.ecosystem.auth.domain.reqres.model.login.Login
import com.google.gson.annotations.SerializedName

data class LoginItem(

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("accessToken")
    val accessToken: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("refreshToken")
    val refreshToken: String? = null,

    @field:SerializedName("phoneNumber")
    val phoneNumber: String? = null
) {
    fun toLogin(): Login {
        return Login(
            id.orEmpty(),
            accessToken.orEmpty(),
            username.orEmpty(),
            refreshToken.orEmpty(),
            phoneNumber.orEmpty()
        )
    }
}
