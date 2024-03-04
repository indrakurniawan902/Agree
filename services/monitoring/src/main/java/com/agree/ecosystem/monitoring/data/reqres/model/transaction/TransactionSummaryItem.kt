package com.agree.ecosystem.monitoring.data.reqres.model.transaction

import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.TransactionSummary
import com.google.gson.annotations.SerializedName

data class TransactionSummaryItem(
    @field:SerializedName("id")
    val id: String? = null,
    @field:SerializedName("subvessel_id")
    val subVesselId: String? = null,
    @field:SerializedName("netto")
    val netto: String? = null,
    @field:SerializedName("fee")
    val fee: Long? = null
) {
    fun toTransactionSummary(): TransactionSummary {
        return TransactionSummary(
            id = id.orEmpty(),
            subVesselId = subVesselId.orEmpty(),
            netto = netto.orEmpty(),
            fee = fee ?: 0L
        )
    }
}
