package com.agree.ecosystem.core.utils.utility.validation.rules.usecase

import android.util.Patterns
import android.view.View
import android.widget.EditText
import com.devbase.presentation.validation.rules.BaseRule
import com.devbase.presentation.validation.rules.RegexRule
import com.google.android.material.textfield.TextInputLayout

class LoginRule(
    val userNameRule: RegexRule,
    val mobileRule: RegexRule
) : BaseRule {

    private var view: View? = null

    override val errorMessage: String
        get() {
            val text = view?.let { extractInputTypedView(it) }
            return if (Patterns.PHONE.matcher(text.orEmpty()).matches()) {
                mobileRule.errorMessage
            } else {
                userNameRule.errorMessage
            }
        }

    override fun validate(view: View): Boolean {
        val text = extractInputTypedView(view)
        this.view = view
        return if (Patterns.PHONE.matcher(text).matches()) {
            mobileRule.validate(view)
        } else {
            userNameRule.validate(view)
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
