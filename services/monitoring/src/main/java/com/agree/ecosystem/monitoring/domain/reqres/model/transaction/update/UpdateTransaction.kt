package com.agree.ecosystem.monitoring.domain.reqres.model.transaction.update

data class UpdateTransaction(
    val dateTime: String,
    val bruto: String,
    val productType: String,
    val offeringPrice: String,
    val note: String,
    val data: ImageData,
    val updateType: String
)

data class ImageData(
    val images: List<String>
)
