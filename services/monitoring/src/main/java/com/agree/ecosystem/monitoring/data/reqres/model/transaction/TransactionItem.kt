package com.agree.ecosystem.monitoring.data.reqres.model.transaction

import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.Transaction
import com.google.gson.annotations.SerializedName

data class TransactionItem(
    @field:SerializedName("id")
    val id: String? = null,
    @field:SerializedName("subvessel_id")
    val subVesselId: String? = null,
    @field:SerializedName("status")
    val status: String? = null,
    @field:SerializedName("netto")
    val netto: String? = null,
    @field:SerializedName("fee")
    val fee: Long? = null,
    @field:SerializedName("product_type")
    val productType: String? = null,
    @field:SerializedName("date_time")
    val dateTime: String? = null
) {
    fun toTransaction(): Transaction {
        return Transaction(
            id = id.orEmpty(),
            subVesselId = subVesselId.orEmpty(),
            status = if (status == "done") Transaction.Status.DONE else Transaction.Status.IN_PROGRESS,
            netto = netto.orEmpty(),
            fee = fee ?: 0L,
            productType = productType.orEmpty(),
            dateTime = dateTime.orEmpty()
        )
    }
}
