package com.agree.ecosystem.smartfarming.data.reqres.model.devicehsitory

import com.agree.ecosystem.smartfarming.domain.reqres.model.devicehistory.DeviceHistory
import com.google.gson.annotations.SerializedName

data class DeviceHistoryItem(
    @field:SerializedName("day")
    val day: Int? = null,
    @field:SerializedName("data")
    val data: List<Data>? = null
) {
    data class Data(
        @field:SerializedName("created_at")
        val createdAt: String? = null
    )

    fun toDeviceHistory(): DeviceHistory {
        return DeviceHistory(
            day = day ?: 0,
            data = data?.map { DeviceHistory.Data(it.createdAt.orEmpty()) }.orEmpty()
        )
    }
}
