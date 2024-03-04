package com.agree.ecosystem.core.utils.utility.validation.rules.usecase

import android.view.View
import android.widget.EditText
import com.devbase.presentation.validation.rules.BaseRule
import com.devbase.presentation.validation.rules.RegexRule
import com.google.android.material.textfield.TextInputLayout

class ResetPasswordRule(
    val mobileRule: RegexRule,
    val emailRule: RegexRule
) : BaseRule {
    private var view: View? = null
    override val errorMessage: String
        get() {
            val text = view?.let { extractInputTypedView(it) }
            return if (text?.contains("@") == true) {
                emailRule.errorMessage
            } else {
                mobileRule.errorMessage
            }
        }

    override fun validate(view: View): Boolean {
        val text = extractInputTypedView(view)
        this.view = view
        return if (text.contains("@")) {
            emailRule.validate(view)
        } else {
            mobileRule.validate(view)
        }
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
