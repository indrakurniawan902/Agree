package com.agree.ecosystem.monitoring.data.reqres.model.transaction.update

import androidx.annotation.Keep
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@Keep
data class UpdateTransactionBodyPost(
    val dateTime: String,
    val bruto: String,
    val productType: String,
    val offeringPrice: String,
    val note: String,
    val updateType: String
) {
    fun toMap(): Map<String, RequestBody> {
        return mapOf(
            "date_time" to dateTime.toRequestBody(MultipartBody.FORM),
            "bruto" to bruto.toRequestBody(MultipartBody.FORM),
            "product_type" to productType.toRequestBody(MultipartBody.FORM),
            "offering_price" to offeringPrice.toRequestBody(MultipartBody.FORM),
            "note" to note.toRequestBody(MultipartBody.FORM),
            "update_type" to updateType.toRequestBody(MultipartBody.FORM)
        )
    }
}

data class ImageArrayBodyPost(
    val file: File
)
