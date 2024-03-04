package com.agree.ecosystem.core.utils.domain.reqres.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class FinanceCustomMapData(
    val key: String,
    val value: String,
    val optionValue: String = ""
) : Parcelable
