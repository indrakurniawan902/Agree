package com.agree.ecosystem.finances.data.reqres.model

import android.text.TextWatcher
import android.widget.AdapterView
import com.agree.ecosystem.finances.utils.DynamicFormFieldType

interface DynamicFormBaseItem {
    val fieldTag: String
    val fieldTagTwo: String
    val type: DynamicFormFieldType
    var errorText: String
    var value: String?
    var valueTwo: String?
    var showError: Boolean
    val isRequired: Boolean
    val isNumeric: Boolean
    val textDetail: String
    val placeholderText: String
    val inputType: Int
    val isEnable: Boolean
    val textWatcher: TextWatcher?
    var unitValue: String?
}

data class SingleField(
    override val fieldTag: String,
    override var errorText: String,
    val hintText: String,
    override val placeholderText: String,
    override val isRequired: Boolean = false,
    override val inputType: Int,
    val defaultVal: String? = "",
    val suffix: String = "",
    val id: String ="",
    override val isEnable: Boolean = true,
    override val textWatcher: TextWatcher? = null,
    val digits: String = ""
) : DynamicFormBaseItem {
    override val fieldTagTwo: String = ""
    override val type: DynamicFormFieldType = DynamicFormFieldType.SINGLE
    override var value: String? = defaultVal
    override var valueTwo: String? = ""
    override var showError: Boolean = false
    override val isNumeric: Boolean = false
    override val textDetail: String = hintText
    override var unitValue: String? = ""
}

/*
data class CalendarField(
    override val fieldTag: String,
    override var errorText: String,
    val hintText: String,
    override val placeholderText: String,
    override val isRequired: Boolean = false,
    override val inputType: Int,
    val defaultValue: String? = "",
    override val isEnable: Boolean = true,
    override val textWatcher: TextWatcher? = null,
    val isErrorOnBottom: Boolean = false
    // show error on bottom of textInputLayout only
) : DynamicFormBaseItem {
    override val fieldTagTwo: String = ""
    override val type: DynamicFormFieldType = DynamicFormFieldType.CALENDAR
    override var value: String? = defaultValue
    override var valueTwo: String? = ""
    override var showError: Boolean = false
    override val isNumeric: Boolean = false
    override val textDetail: String = hintText
    var additionalError = ""
    override var unitValue: String? = ""
}

data class DropdownPage(
    override val fieldTag: String,
    override var errorText: String,
    val hintText: String,
    override val placeholderText: String,
    val dropdownList: ArrayList<String>,
    override val isRequired: Boolean = false,
    val onClickListener: ((value: String) -> Unit)? = null,
    override val isNumeric: Boolean = false,
    override val inputType: Int,
    var defaultValue: String? = "",
    override val isEnable: Boolean = true,
    override val textWatcher: TextWatcher? = null,
    val searchActivityType: SearchActivityType? = null,
    val lenderName: String? = "",
    val additionalArgs: List<String>? = null
) : DynamicFormBaseItem {
    override val fieldTagTwo: String = ""
    override val type: DynamicFormFieldType = DynamicFormFieldType.DROPDOWNPAGE
    override var value: String? = defaultValue
    override var valueTwo: String? = ""
    override var showError: Boolean = false
    override val textDetail: String = hintText
    override var unitValue: String? = ""
}

data class UnitField(
    override val fieldTag: String,
    override var errorText: String,
    val hintText: String,
    override val placeholderText: String,
    val unitStr: ArrayList<String>,
    override val isRequired: Boolean = false,
    var isDisabled: Boolean = false,
    val onUnitClickListener: ((value: String) -> Unit)? = null,
    val onTextChanged: ((String) -> Unit)? = null,
    override val inputType: Int,
    override val isEnable: Boolean = true,
    override val textWatcher: TextWatcher? = null,
    val defaultVal: String? = "",
    val selectedUnit: String? = ""
) : DynamicFormBaseItem {
    override val fieldTagTwo: String = ""
    override val type: DynamicFormFieldType = DynamicFormFieldType.WITH_UNIT
    override var value: String? = defaultVal
    override var valueTwo: String? = ""
    override var showError: Boolean = false
    var choosenUnit: String = if (unitStr.isNotEmpty()) unitStr[0] else ""
    var additionalError = ""
    override val isNumeric: Boolean = true
    override val textDetail: String = hintText
    override var unitValue: String? = selectedUnit
}
 */

data class DropdownField(
    override val fieldTag: String,
    override var errorText: String,
    val hintText: String,
    override var value: String? = "",
    override val placeholderText: String,
    val dropdownList: List<String>,
    override val isRequired: Boolean = false,
    val onClickListener: ((value: String) -> Unit)? = null,
    override val isNumeric: Boolean = false,
    override val inputType: Int,
    override val isEnable: Boolean = true,
    override val textWatcher: TextWatcher? = null,
    val isErrorOnBottom: Boolean = false,
    var selectedIndex: Int? = null,
    val onItemChangedListener: AdapterView.OnItemSelectedListener? = null
    // show error on bottom of textInputLayout only
) : DynamicFormBaseItem {
    override val fieldTagTwo: String = ""
    override val type: DynamicFormFieldType = DynamicFormFieldType.DROPDOWN
    override var valueTwo: String? = ""
    override var showError: Boolean = false
    override val textDetail: String = hintText
    override var unitValue: String? = ""
}