package com.agree.ui.data.reqres.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FormEntity(
    var componentType: String = "",
    var min: Int = 0,
    var max: Int = 0,
    var prefix: String = "",
    var options: String = "",
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    var label: String = "",
    var suffix: String = "",
    var fieldType: String = "",
    var value: String = "",
    var required: Boolean = false,
    var helper: String = "",
    var placeHolder: String = "",
    var dataName: String = "",
    var hasSubForm: Boolean = false,
    var identifierId: String
)
