package com.agree.ecosystem.auth.presentation.menu.resetpassword

import android.view.View
import com.agree.ecosystem.auth.R
import com.agree.ecosystem.auth.databinding.FragmentCreateNewPasswordBinding
import com.agree.ecosystem.auth.presentation.menu.changepassword.ChangePasswordDataContract
import com.agree.ecosystem.auth.presentation.navigation.AuthNavigation
import com.agree.ecosystem.core.utils.base.AgrFormFragment
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.core.utils.utility.validation.addRule
import com.agree.ecosystem.core.utils.utility.validation.initState
import com.agree.ecosystem.core.utils.utility.validation.validation
import com.devbase.presentation.validation.rules.RegexRule
import com.devbase.presentation.validation.util.notEmptyRule
import com.devbase.presentation.validation.util.sameValueRule
import com.devbase.utils.util.getStringResource
import com.telkom.legion.component.textfield.base.LgnTextField

class CreateNewPasswordFragment : AgrFormFragment<FragmentCreateNewPasswordBinding>(), ChangePasswordDataContract {
    override fun initAction() {
        super.initAction()
        with(binding) {
            btnSave.setOnClickListener {
                authNav.fromCreateNewPasswordToLogin()
            }
        }
    }
    override fun initForm() {
        with(binding) {

            toolbar.setBackButtonOnClick { requireActivity().onNavigateUp() }

            registerFormState(
                etNewPassword.initState(),
                etNewPasswordConfirmation.initState()
            )
            etNewPassword.addRule(
                etNewPassword.state,
                RegexRule("^.{8,}\$", getStringResource(R.string.label_error_password_min_8_character)),
                notEmptyRule(getStringResource(R.string.error_password_empty))
            )
            etNewPasswordConfirmation.addRule(
                etNewPasswordConfirmation.state,
                notEmptyRule(getStringResource(R.string.error_password_confirmation_empty)),
                sameValueRule({ etNewPassword.text }, getStringResource(R.string.error_same_value))
            )
        }
    }

    override fun onAllFormValidated() {
        with(binding) {
            btnSave.isEnable = true
            etNewPasswordConfirmation.success = getStringResource(R.string.label_success_password_matching)
        }
    }

    override fun onFormValidated(view: View) {
        with(binding) {

            if (view.id == etNewPassword.id && isPasswordFocuses) {
                etNewPasswordConfirmation.editText?.setSelection(etNewPasswordConfirmation.text.length)
            }
            if (view is LgnTextField) view.error = ""
        }
    }

    override fun onFormUnvalidated(view: View, errorMessage: String) {
        if (view is LgnTextField) {
            view.error = errorMessage
            binding.btnSave.isEnable = false
        }
    }

    override fun onLoading() {
        TODO("Not yet implemented")
    }

    override fun onChangePasswordSuccess() {
        TODO("Not yet implemented")
    }

    override fun onChangePasswordFailed(e: Throwable?) {
        TODO("Not yet implemented")
    }

    override fun isValid(): Boolean {
        var isAllValid = true

        if (!passwordValidation.value.isValid) onFormUnvalidated(
            passwordValidation.value.view,
            if (passwordValidation.value.errorMessage == "") getString(R.string.error_password_empty) else passwordValidation.value.errorMessage
        )
        isAllValid = passwordValidation.value.isValid && isAllValid

        if (!confirmPasswordValidation.value.isValid) onFormUnvalidated(
            confirmPasswordValidation.value.view,
            if (confirmPasswordValidation.value.errorMessage == "") getString(R.string.error_password_confirmation_empty) else confirmPasswordValidation.value.errorMessage
        )
        isAllValid = confirmPasswordValidation.value.isValid && isAllValid

        return isAllValid
    }

    private val passwordValidation by validation { etNewPassword }
    private val confirmPasswordValidation by validation { etNewPasswordConfirmation }
    private var isPasswordFocuses = false
    private val authNav: AuthNavigation by navigation { activity }
}
