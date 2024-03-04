package com.agree.ui.data.reqres.model

import com.agree.ui.domain.model.DynamicFormSchema
import com.agree.ui.utils.enums.FormInputType
import com.agree.ui.utils.enums.FormType
import com.google.gson.annotations.SerializedName

data class DynamicFormSchemaItem(

    @field:SerializedName("componentType")
    val componentType: String? = null,

    @field:SerializedName("min")
    val min: Int? = null,

    @field:SerializedName("max")
    val max: Int? = null,

    @field:SerializedName("prefix")
    val prefix: String? = null,

    @field:SerializedName("options")
    val options: List<String>? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("label")
    val label: String? = null,

    @field:SerializedName("suffix")
    val suffix: String? = null,

    @field:SerializedName("fieldType")
    val fieldType: String? = null,

    @field:SerializedName("value")
    val value: String? = null,

    @field:SerializedName("required")
    val required: Boolean? = null,

    @field:SerializedName("helper")
    val helper: String? = null,

    @field:SerializedName("dataName")
    var dataName: String? = null,

    @field:SerializedName("placeHolder")
    val placeHolder: String? = null,

    @field:SerializedName("hasSubForm")
    var hasSubForm: Boolean? = null,

    @field:SerializedName("subForm")
    var subForm: List<SubForm>? = null,

) {
    fun toDynamicFormSchema(): DynamicFormSchema {
        return DynamicFormSchema(
            FormType.toFormType(componentType.orEmpty()),
            min ?: 0,
            max ?: 0,
            prefix.orEmpty(),
            options ?: emptyList(),
            id.orEmpty(),
            label.orEmpty(),
            suffix.orEmpty(),
            FormInputType.toInputType(fieldType.orEmpty()),
            value.orEmpty(),
            required ?: false,
            helper.orEmpty(),
            placeHolder.orEmpty(),
            dataName.orEmpty(),
            hasSubForm ?: false,
            subForm ?: emptyList()
        )
    }
}
