package com.agree.ui.data.reqres.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubForm(
    val displayedSchema: List<DisplayedSchema>,
    val triggerValue: String
) : Parcelable
