package com.agree.ecosystem.finances.domain.reqres.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class CultivatorBorrower(
    val borrowerId: String,
    val name: String,
    val nik: String,
    val image: String
) : Parcelable
