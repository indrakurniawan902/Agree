package com.agree.ecosystem.monitoring.presentation.navigation.params

import android.os.Parcelable
import androidx.annotation.Keep
import com.agree.ecosystem.monitoring.domain.reqres.model.insertactivitysop.InsertActivitySopBundleParams
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DetailActivityParams(
    val idBundleParams: InsertActivitySopBundleParams,
    val order: String = "1",
    val date: String,
    val activityName: String,
    val guideContent: String,
    val isCompleted: Boolean,
    val type: String,
    val recordType: String
) : Parcelable
