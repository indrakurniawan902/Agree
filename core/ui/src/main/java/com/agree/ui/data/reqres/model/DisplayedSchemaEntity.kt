package com.agree.ui.data.reqres.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DisplayedSchemaEntity(
    val code: String,
    val identifierId: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)
