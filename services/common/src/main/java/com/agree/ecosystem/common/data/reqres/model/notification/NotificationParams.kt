package com.agree.ecosystem.common.data.reqres.model.notification

import androidx.annotation.Keep

@Keep
data class NotificationParams(
    val quantity: Int,
    val page: Int,
    val idUser: String
)
