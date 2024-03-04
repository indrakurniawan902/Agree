package com.agree.ecosystem.monitoring.data.reqres.model.transaction.insert

import androidx.annotation.Keep
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.insert.ImageData
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.insert.InsertTransaction
import com.google.gson.annotations.SerializedName

@Keep
data class InsertTransactionItem(
    @field:SerializedName("subvessel_id")
    val subVesselId: String? = null,
    @field:SerializedName("date_time")
    val dateTime: String? = null,
    @field:SerializedName("bruto")
    val bruto: String? = null,
    @field:SerializedName("product_type")
    val productType: String? = null,
    @field:SerializedName("offering_price")
    val offeringPrice: String? = null,
    @field:SerializedName("note")
    val note: String? = null,
    @field:SerializedName("data")
    val data: ImageDataItem? = null
) {
    fun toInsertTransaction(): InsertTransaction {
        return InsertTransaction(
            subVesselId = subVesselId.orEmpty(),
            dateTime = dateTime.orEmpty(),
            bruto = bruto.orEmpty(),
            productType = productType.orEmpty(),
            offeringPrice = offeringPrice.orEmpty(),
            note = note.orEmpty(),
            data = ImageData(
                images = data?.images?.map {
                    it
                }.orEmpty()
            ),
        )
    }
}

data class ImageDataItem(
    @field:SerializedName("images")
    val images: List<String>
)
