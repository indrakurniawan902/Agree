package com.agree.ecosystem.smartfarming.data.reqres.model

import com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming.UpdateReadStatus
import com.google.gson.annotations.SerializedName

data class UpdateReadStatusItem(
    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String? = null
) {
    fun toUpdateReadStatus() : UpdateReadStatus {
        return UpdateReadStatus(
            success = success,
            message = message.orEmpty()
        )
    }
}
