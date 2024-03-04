package com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop

import android.os.Parcelable
import com.agree.ui.data.reqres.model.FormItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class FormSchema(
    val activeformSchema: List<ActiveFormSchema>,
    val formSchemaItem: List<FormItem>
) : Parcelable
