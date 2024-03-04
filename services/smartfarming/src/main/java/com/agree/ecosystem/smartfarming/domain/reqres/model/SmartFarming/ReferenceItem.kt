package com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming

import android.os.Parcelable
import androidx.annotation.Keep
import com.agree.ecosystem.smartfarming.R
import com.agree.ui.UiKitAttrs
import com.devbase.utils.util.getStringResource
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class ReferenceItem(

    @field:SerializedName("parameter")
    val parameter: List<ParameterItem>? = mutableListOf(),

    @field:SerializedName("recommendation")
    val recommendation: List<RecommendationItem>? = mutableListOf(),

    @field:SerializedName("status")
    val status: Status
): Parcelable

{
    enum class Status(val status: String, val textColor: Int) {
        GOOD(
            getStringResource(R.string.good),
            UiKitAttrs.success_normal
        ),
        WARY(
            getStringResource(R.string.wary),
            UiKitAttrs.warning_normal
        ),
        BAD(
            getStringResource(R.string.bad),
            UiKitAttrs.error_normal
        ),
    }
}