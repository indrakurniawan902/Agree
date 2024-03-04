package com.agree.ecosystem.monitoring.data.reqres.model.transaction.update

import androidx.annotation.Keep
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.update.ImageData
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.update.UpdateTransaction
import com.google.gson.annotations.SerializedName

@Keep
data class UpdateTransactionItem(
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
    val data: ImageDataItem? = null,
    @field:SerializedName("update_type")
    val updateType: String? = null,
) {
    fun toUpdateTransaction(): UpdateTransaction {
        return UpdateTransaction(
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
            updateType = updateType.orEmpty()
        )
    }
}

data class ImageDataItem(
    @field:SerializedName("images")
    val images: List<String>
)
