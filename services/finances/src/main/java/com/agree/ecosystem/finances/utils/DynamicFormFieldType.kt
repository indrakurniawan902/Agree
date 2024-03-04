package com.agree.ecosystem.finances.utils

enum class DynamicFormFieldType(val type: String) {
    SINGLE("inputText"), DROPDOWN("inputDropdown"), RADIO("radioButton"), DATE("inputDate");

    companion object {
        fun toFormType(type: String): DynamicFormFieldType {
            return when (type) {
                DROPDOWN.type -> DROPDOWN
                RADIO.type -> RADIO
                else -> SINGLE
            }
        }
    }
}
