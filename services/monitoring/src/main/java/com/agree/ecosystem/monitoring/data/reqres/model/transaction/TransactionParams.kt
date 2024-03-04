package com.agree.ecosystem.monitoring.data.reqres.model.transaction

import androidx.annotation.Keep
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

@Keep
data class TransactionParams(
    val page: Int,
    val quantity: Int,
    val query: Query,
) {
    @Keep
    data class Query(
        @field:SerializedName("subvessel_id")
        val subVesselId: String
    ) {
        fun toQueryString(): String {
            return Gson().toJson(this)
        }
    }
}
