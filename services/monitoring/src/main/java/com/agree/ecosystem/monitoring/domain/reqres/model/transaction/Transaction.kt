package com.agree.ecosystem.monitoring.domain.reqres.model.transaction

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Transaction(
    val id: String,
    val subVesselId: String,
    val status: Status,
    val netto: String,
    val fee: Long,
    val productType: String,
    val dateTime: String
) : Parcelable {
    enum class Status {
        IN_PROGRESS,
        DONE
    }
}
