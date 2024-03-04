package com.agree.ecosystem.core.utils.utility.validation.viewmodels

import android.view.View
import com.telkom.legion.component.checkbox.base.LgnCheckBox
import com.telkom.legion.component.radio.base.LgnRadioButton

interface ValidationView {
    fun onAllFormValidated()
    fun onFormUnvalidated(view: View, errorMessage: String) {
        if (view !is LgnRadioButton && view !is LgnCheckBox && errorMessage.isNotEmpty()) {
            view.requestFocus()
        }
    }
    fun onFormValidated(view: View)
}
