package com.agree.ecosystem.utilities.domain.reqres.model.commodity

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Commodity(
    val id: String,
    val name: String,
    val target: String,
    val bruto: String,
    val netto: String,
    val image: String,
    val subSectorId: String
) : Parcelable {
    constructor(id: String, name: String) : this(id, name, "", "0", "0", "", "")
}
