package com.agree.ecosystem.monitoring.domain.reqres.model.transaction

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class TransactionDetail(
    val id: String,
    val subVesselId: String,
    val status: String,
    val fee: Double,
    val productType: String,
    val dateTime: String,
    val bruto: Double,
    val netto: Double,
    val createdAt: String,
    val createdBy: String,
    val modifiedAt: String,
    val price: Double,
    val offeringPrice: Double,
    val settlementDateTime: String,
    val note: String,
    val description: String,
    val images: List<String>,
    val responseImages: List<String>
) : Parcelable
