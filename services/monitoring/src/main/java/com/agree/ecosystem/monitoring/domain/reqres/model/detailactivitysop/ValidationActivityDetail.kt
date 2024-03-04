package com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ValidationActivityDetail(
    val is_edit: Boolean
) : Parcelable
