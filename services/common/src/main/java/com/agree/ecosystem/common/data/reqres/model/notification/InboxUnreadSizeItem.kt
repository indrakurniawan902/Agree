package com.agree.ecosystem.common.data.reqres.model.notification

import com.agree.ecosystem.common.domain.reqres.model.notification.UnreadInbox
import com.google.gson.annotations.SerializedName

data class InboxUnreadSizeItem(
    @field:SerializedName("total_inbox")
    val totalInbox: Int? = 0,

    @field:SerializedName("total_read_inbox")
    val totalReadInbox: Int? = 0,

    @field:SerializedName("total_unread_inbox")
    val totalUnreadInbox: Int? = 0
) {
    fun toUnreadInbox(): UnreadInbox {
        return UnreadInbox(
            totalInbox ?: 0,
            totalReadInbox ?: 0, totalUnreadInbox ?: 0
        )
    }
}
