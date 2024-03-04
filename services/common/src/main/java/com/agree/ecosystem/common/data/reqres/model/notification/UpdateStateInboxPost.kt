package com.agree.ecosystem.common.data.reqres.model.notification

import com.google.gson.annotations.SerializedName

data class UpdateStateInboxPost(
    @SerializedName("type") val type: String = "read",
    @SerializedName("is_read") val isRead: Boolean = false
)
