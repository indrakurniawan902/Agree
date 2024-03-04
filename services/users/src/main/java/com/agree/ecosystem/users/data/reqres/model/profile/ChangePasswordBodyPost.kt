package com.agree.ecosystem.users.data.reqres.model.profile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ChangePasswordBodyPost(
    @SerializedName("id")val id: String,
    @SerializedName("password")val password: String
)
