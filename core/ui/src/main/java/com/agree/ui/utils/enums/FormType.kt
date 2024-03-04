package com.agree.ui.utils.enums

enum class FormType(val type: String) {
    FORM_COMP_TYPE_DATE("date"),
    FORM_COMP_TYPE_TEXT("text"),
    FORM_COMP_TYPE_DROPDOWN("dropdown"),
    FORM_COMP_TYPE_TEXT_AREA("text area"),
    FORM_COMP_TYPE_PASSWORD("password"),
    FORM_COMP_TYPE_UNIT("unit"),
    FORM_COMP_TYPE_TIME("time"),
    FORM_COMP_TYPE_RADIO("radio"),
    FORM_COMP_TYPE_CHECKBOX("checkbox"),
    FORM_COMP_TYPE_CHIPS("chips"),
    FORM_COMP_TYPE_PHOTO_UPLOAD("photo");

    companion object {
        fun toFormType(type: String): FormType {
            return when (type) {
                FORM_COMP_TYPE_DATE.type -> FORM_COMP_TYPE_DATE
                FORM_COMP_TYPE_DROPDOWN.type -> FORM_COMP_TYPE_DROPDOWN
                FORM_COMP_TYPE_TEXT_AREA.type -> FORM_COMP_TYPE_TEXT_AREA
                FORM_COMP_TYPE_PASSWORD.type -> FORM_COMP_TYPE_PASSWORD
                FORM_COMP_TYPE_UNIT.type -> FORM_COMP_TYPE_UNIT
                FORM_COMP_TYPE_TIME.type -> FORM_COMP_TYPE_TIME
                FORM_COMP_TYPE_RADIO.type -> FORM_COMP_TYPE_RADIO
                FORM_COMP_TYPE_PHOTO_UPLOAD.type -> FORM_COMP_TYPE_PHOTO_UPLOAD
                FORM_COMP_TYPE_CHECKBOX.type -> FORM_COMP_TYPE_CHECKBOX
                FORM_COMP_TYPE_CHIPS.type -> FORM_COMP_TYPE_CHIPS
                else -> FORM_COMP_TYPE_TEXT
            }
        }
    }
}
