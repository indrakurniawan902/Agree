package com.agree.ecosystem.monitoring.data.reqres.model.transaction

import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.TransactionDetail
import com.google.gson.annotations.SerializedName

data class TransactionDetailItem(
    @field:SerializedName("id")
    val id: String?,
    @field:SerializedName("subvessel_id")
    val subVesselId: String?,
    @field:SerializedName("status")
    val status: String?,
    @field:SerializedName("fee")
    val fee: Double?,
    @field:SerializedName("product_type")
    val productType: String?,
    @field:SerializedName("date_time")
    val dateTime: String?,
    @field:SerializedName("bruto")
    val bruto: Double?,
    @field:SerializedName("netto")
    val netto: Double?,
    @field:SerializedName("created_at")
    val createdAt: String?,
    @field:SerializedName("created_by")
    val createdBy: String?,
    @field:SerializedName("modified_at")
    val modifiedAt: String?,
    @field:SerializedName("price")
    val price: Double?,
    @field:SerializedName("offering_price")
    val offeringPrice: Double?,
    @field:SerializedName("settlement_date_time")
    val settlementDateTime: String?,
    @field:SerializedName("note")
    val note: String?,
    @field:SerializedName("description")
    val description: String?,
    @field:SerializedName("images")
    val images: List<String>?,
    @field:SerializedName("response_images")
    val responseImages: List<String>?
) {
    fun toTransactionDetail(): TransactionDetail {
        return TransactionDetail(
            id = id.orEmpty(),
            subVesselId = subVesselId.orEmpty(),
            status = status.orEmpty(),
            fee = fee ?: 0.toDouble(),
            productType = productType.orEmpty(),
            dateTime = dateTime.orEmpty(),
            bruto = bruto ?: 0.toDouble(),
            netto = netto ?: 0.toDouble(),
            createdAt = createdAt.orEmpty(),
            createdBy = createdBy.orEmpty(),
            modifiedAt = modifiedAt.orEmpty(),
            price = price ?: 0.toDouble(),
            offeringPrice = offeringPrice ?: 0.toDouble(),
            settlementDateTime = settlementDateTime.orEmpty(),
            note = note.orEmpty(),
            description = description.orEmpty(),
            images = images?.map { it }.orEmpty(),
            responseImages = responseImages?.map { it }.orEmpty()
        )
    }
}
