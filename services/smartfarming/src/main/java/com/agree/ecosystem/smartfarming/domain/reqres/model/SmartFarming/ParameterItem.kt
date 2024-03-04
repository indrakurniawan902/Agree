package com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ParameterItem(

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("value")
    val value: String? = null,

    @field:SerializedName("suffix")
    val suffix: String? = null,
): Parcelable