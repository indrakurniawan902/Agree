package com.agree.ecosystem.core.utils.data.reqres.model.token

import androidx.annotation.Keep

@Keep
data class RefreshTokenBodyPost(
    val refreshToken: String
)
