package com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class SmartfarmReference(

    @field:SerializedName("reference")
    val reference: List<ReferenceItem>? = mutableListOf(),

    @field:SerializedName("key")
    val key: String? = null
): Parcelable