package com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActiveFormSchema(
    val id: String,
    var value: String,
    val is_reference: String? = null
) : Parcelable
