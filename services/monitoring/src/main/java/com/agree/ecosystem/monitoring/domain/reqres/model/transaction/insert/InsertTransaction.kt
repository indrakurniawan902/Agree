package com.agree.ecosystem.monitoring.domain.reqres.model.transaction.insert

data class InsertTransaction(
    val subVesselId: String,
    val dateTime: String,
    val bruto: String,
    val productType: String,
    val offeringPrice: String,
    val note: String,
    val data: ImageData
)

data class ImageData(
    val images: List<String>
)
