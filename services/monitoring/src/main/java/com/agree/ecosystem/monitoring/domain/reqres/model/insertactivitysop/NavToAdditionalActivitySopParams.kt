package com.agree.ecosystem.monitoring.domain.reqres.model.insertactivitysop

import android.os.Parcelable
import androidx.annotation.Keep
import com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop.FormSchema
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class NavToAdditionalActivitySopParams(
    val idBundleParams: InsertActivitySopBundleParams,
    val phaseName: String,
    val formSchema: FormSchema?,
    val date: String,
    val order: String = "1",
    val isInsert: Boolean
) : Parcelable
