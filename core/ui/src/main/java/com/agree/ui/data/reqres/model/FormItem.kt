package com.agree.ui.data.reqres.model

import android.os.Parcelable
import com.agree.ui.domain.model.DynamicFormSchema
import com.agree.ui.utils.enums.FormInputType
import com.agree.ui.utils.enums.FormType
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FormItem(
    @field:SerializedName("componentType")
    var componentType: String = "",

    @field:SerializedName("min")
    var min: Int = 0,

    @field:SerializedName("max")
    var max: Int = 0,

    @field:SerializedName("prefix")
    var prefix: String = "",

    @field:SerializedName("options")
    var options: List<String> = listOf(),

    @field:SerializedName("id")
    var id: String = "",

    @field:SerializedName("label")
    var label: String = "",

    @field:SerializedName("suffix")
    var suffix: String = "",

    @field:SerializedName("fieldType")
    var fieldType: String = "",

    @field:SerializedName("value")
    var value: String = "",

    @field:SerializedName("required")
    var required: Boolean = false,

    @field:SerializedName("helper")
    var helper: String = "",

    @field:SerializedName("placeholder")
    var placeHolder: String = "",

    @field:SerializedName("dataName")
    var dataName: String = "",

    @field:SerializedName("hasSubForm")
    var hasSubForm: Boolean = false,

    @field:SerializedName("subForm")
    var subForm: List<SubForm> = listOf(),

) : Parcelable {
    fun toDynamicFormSchema(isEnable: Boolean = true): DynamicFormSchema {
        return DynamicFormSchema(
            FormType.toFormType(componentType),
            min,
            max,
            prefix,
            options,
            id,
            label,
            suffix,
            FormInputType.toInputType(fieldType),
            value,
            required,
            helper,
            placeHolder,
            dataName,
            hasSubForm,
            subForm,
            isEnable
        )
    }
}
