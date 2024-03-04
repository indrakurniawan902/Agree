package com.agree.ecosystem.partnership.data.reqres.model.company

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class CompanyParams(
    val page: Int,
    val quantity: Int,
    val subSectorId: String,
    val userId: String,
    val search: String
) : Parcelable
