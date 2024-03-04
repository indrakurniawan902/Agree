package com.agree.ecosystem.monitoring.data.reqres.model.incident

import androidx.annotation.Keep
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@Keep
data class AddIncidentBodyPost(
    val dateTime: String,
    val subVesselId: String,
    val category: String,
    val incident: String,
    val population: String,
    val actionTaken: String,
    val cost: String,
    val detail: String,
) {
    fun toMap(): Map<String, RequestBody> {
        return mapOf(
            "date_time" to dateTime.toRequestBody(MultipartBody.FORM),
            "subvessel_id" to subVesselId.toRequestBody(MultipartBody.FORM),
            "category" to category.toRequestBody(MultipartBody.FORM),
            "incident" to incident.toRequestBody(MultipartBody.FORM),
            "population" to population.toRequestBody(MultipartBody.FORM),
            "action_taken" to actionTaken.toRequestBody(MultipartBody.FORM),
            "cost" to cost.toRequestBody(MultipartBody.FORM),
            "detail" to detail.toRequestBody(MultipartBody.FORM)
        )
    }
}

data class ImageArrayBodyPost(
    val file: File
)
