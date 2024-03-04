package com.agree.ui.utils.enums

import android.text.InputType

enum class FormInputType(val inputType: String) {
    INPUT_TYPE_DATE("date"),
    INPUT_TYPE_DATE_TIME("date time"),
    INPUT_TYPE_NUMBER("number"),
    INPUT_TYPE_DECIMAL("decimal"),
    INPUT_TYPE_TIME("time"),
    INPUT_TYPE_CURRENCY("currency"),
    INPUT_TYPE_PHONE("phone"),
    INPUT_TYPE_EMAIL("email"),
    INPUT_TYPE_TEXT("text"),
    INPUT_TYPE_PASSWORD("password"),
    INPUT_TYPE_NUMBER_PASSWORD("number password");

    companion object {
        fun toInputType(inputType: String): FormInputType {
            return when (inputType) {
                INPUT_TYPE_DATE.inputType -> INPUT_TYPE_DATE
                INPUT_TYPE_DATE_TIME.inputType -> INPUT_TYPE_DATE_TIME
                INPUT_TYPE_NUMBER.inputType -> INPUT_TYPE_NUMBER
                INPUT_TYPE_DECIMAL.inputType -> INPUT_TYPE_DECIMAL
                INPUT_TYPE_TIME.inputType -> INPUT_TYPE_TIME
                INPUT_TYPE_CURRENCY.inputType -> INPUT_TYPE_CURRENCY
                INPUT_TYPE_PHONE.inputType -> INPUT_TYPE_PHONE
                INPUT_TYPE_EMAIL.inputType -> INPUT_TYPE_EMAIL
                INPUT_TYPE_PASSWORD.inputType -> INPUT_TYPE_PASSWORD
                INPUT_TYPE_NUMBER_PASSWORD.inputType -> INPUT_TYPE_NUMBER_PASSWORD
                else -> INPUT_TYPE_TEXT
            }
        }

        fun parseInputType(type: FormInputType): Int {
            return when (type) {
                INPUT_TYPE_DATE -> InputType.TYPE_DATETIME_VARIATION_DATE
                INPUT_TYPE_DATE_TIME -> InputType.TYPE_DATETIME_VARIATION_NORMAL
                INPUT_TYPE_NUMBER, INPUT_TYPE_DECIMAL, INPUT_TYPE_CURRENCY -> InputType.TYPE_CLASS_NUMBER
                INPUT_TYPE_TIME -> InputType.TYPE_DATETIME_VARIATION_TIME
                INPUT_TYPE_PHONE -> InputType.TYPE_CLASS_PHONE
                INPUT_TYPE_EMAIL -> InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                INPUT_TYPE_PASSWORD -> InputType.TYPE_TEXT_VARIATION_PASSWORD
                INPUT_TYPE_NUMBER_PASSWORD -> InputType.TYPE_NUMBER_VARIATION_PASSWORD
                else -> InputType.TYPE_CLASS_TEXT
            }
        }
    }
}
