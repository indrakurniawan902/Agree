package com.agree.ecosystem.smartfarming.domain.reqres.model.devicehistory

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeviceHistory(
    val day: Int,
    val data: List<Data>
): Parcelable {
    @Parcelize
    data class Data(
        val createdAt: String
    ): Parcelable
}