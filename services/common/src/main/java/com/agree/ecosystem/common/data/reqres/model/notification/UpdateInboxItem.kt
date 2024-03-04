package com.agree.ecosystem.common.data.reqres.model.notification

import com.agree.ecosystem.common.domain.reqres.model.notification.UpdateInbox
import com.google.gson.annotations.SerializedName

data class UpdateInboxItem(
    @field:SerializedName("Data")
    val data: Int? = null,

    @field:SerializedName("MetaData")
    val metaData: String? = null,

    @field:SerializedName("Error")
    val error: String? = null,

    @field:SerializedName("Count")
    val count: Int? = 0
) {
    fun toUpdateInbox() = UpdateInbox(
        data = data ?: 0,
        metaData = metaData.orEmpty(),
        error = error.orEmpty(),
        count = count ?: 0
    )
}
