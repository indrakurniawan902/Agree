package com.agree.ecosystem.auth.data.reqres.model.logout

import androidx.annotation.Keep

@Keep
data class LogoutBodyPost(
    val firebaseToken: String
)
