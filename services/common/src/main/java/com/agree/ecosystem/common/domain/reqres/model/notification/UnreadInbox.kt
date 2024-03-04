package com.agree.ecosystem.common.domain.reqres.model.notification

import androidx.annotation.Keep

@Keep
data class UnreadInbox(
    val totalInbox: Int,
    val totalReadInbox: Int,
    val totalUnreadInbox: Int
)
