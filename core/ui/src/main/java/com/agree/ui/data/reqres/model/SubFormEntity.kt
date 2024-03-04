package com.agree.ui.data.reqres.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SubFormEntity(
    val triggerValue: String,
    val identifierId: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)
