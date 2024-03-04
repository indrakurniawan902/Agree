package com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming

import android.os.Parcelable
import androidx.annotation.Keep
import com.agree.ecosystem.smartfarming.R
import com.agree.ui.UiKitAttrs
import com.devbase.utils.util.getStringResource
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ParameterTestFormSchema (
    val icon: String,
    val label: String,
    val key: String,
    val raw_value: String,
    val smartfarm_reference:SmartfarmReference?,
    val value: String,
    val isRead: Boolean,
    val status: Status,
    val status_recommendation: List<RecommendationItem>? = mutableListOf()
    ) : Parcelable
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