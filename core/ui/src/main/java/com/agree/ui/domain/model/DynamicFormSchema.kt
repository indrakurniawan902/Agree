package com.agree.ui.domain.model

import com.agree.ui.data.reqres.model.SubForm
import com.agree.ui.utils.enums.FormInputType
import com.agree.ui.utils.enums.FormType

data class DynamicFormSchema(
    val componentType: FormType,
    val min: Int,
    val max: Int,
    val prefix: String,
    val options: List<String>,
    val id: String,
    val label: String,
    val suffix: String,
    val fieldType: FormInputType,
    val value: String,
    val required: Boolean,
    val helper: String,
    val placeHolder: String,
    val dataName: String,
    val hasSubForm: Boolean,
    val subForm: List<SubForm>,
    var isEnable: Boolean = false
)
