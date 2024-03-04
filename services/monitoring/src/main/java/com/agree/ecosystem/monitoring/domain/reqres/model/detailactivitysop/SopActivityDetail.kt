package com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop

import android.os.Parcelable
import androidx.annotation.Keep
import com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop.FormSchema
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class SopActivityDetail(
    val formSchema: FormSchema,
    val id: String,
    val name: String,
    val isInsert: Boolean,
    val order: String?,
    val repetition: Int
) : Parcelable
