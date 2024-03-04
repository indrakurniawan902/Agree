package com.agree.ecosystem.core.utils.utility.validation.rules

import android.view.View
import android.widget.EditText
import com.devbase.presentation.validation.rules.BaseRule
import com.devbase.presentation.validation.util.CommonRegex
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

class EmailRule(override val errorMessage: String) : BaseRule {

    override fun validate(view: View): Boolean {
        val text = extractInputTypedView(view)
        return if (text.isEmpty()) true
        else Pattern.compile(CommonRegex.EMAIL).matcher(text).matches()
    }

    private fun extractInputTypedView(view: View): String {
        return when (view) {
            is EditText -> {
                view.text.toString().trim()
            }

            is TextInputLayout -> {
                view.editText?.text.toString().trim()
            }

            else -> {
                throw com.devbase.presentation.validation.util.error
            }
        }
    }
}
