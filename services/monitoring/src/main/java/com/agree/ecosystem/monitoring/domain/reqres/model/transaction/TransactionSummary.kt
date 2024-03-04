package com.agree.ecosystem.monitoring.domain.reqres.model.transaction

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class TransactionSummary(
    val id: String,
    val subVesselId: String,
    val netto: String,
    val fee: Long
) : Parcelable {
    fun isEmpty(): Boolean {
        return id.isEmpty() && netto == "0" && fee == 0L && subVesselId.isEmpty()
    }
}
