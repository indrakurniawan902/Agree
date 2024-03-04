package com.agree.ecosystem.auth.presentation.menu.resetpassword

import android.view.View
import com.agree.ecosystem.auth.R
import com.agree.ecosystem.auth.databinding.FragmentResetPasswordBinding
import com.agree.ecosystem.auth.presentation.navigation.AuthNavigation
import com.agree.ecosystem.auth.presentation.navigation.navparams.verification.InputTypeParams
import com.agree.ecosystem.auth.presentation.navigation.navparams.verification.VerificationTypeParams
import com.agree.ecosystem.core.utils.base.AgrFormFragment
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.core.utils.utility.validation.addRule
import com.agree.ecosystem.core.utils.utility.validation.initState
import com.agree.ecosystem.core.utils.utility.validation.rules.mobileNumberOnlyRule
import com.agree.ecosystem.core.utils.utility.validation.rules.usecase.ResetPasswordRule
import com.devbase.presentation.validation.util.emailRule
import com.devbase.presentation.validation.util.notEmptyRule
import com.devbase.utils.util.getDrawableResource
import com.devbase.utils.util.getStringResource
import com.telkom.legion.component.textfield.base.LgnTextField

class ResetPasswordFragment : AgrFormFragment<FragmentResetPasswordBinding>() {

    override fun initUI() {
        super.initUI()
        with(binding) {
            toolbar.navigationIcon =
                getDrawableResource(com.telkom.legion.component.R.drawable.ic_close)
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }

            btnSendCode.setOnClickListener {
                if (etEmailAndNoHandPhone.text.contains("@")) {
                    authNav.fromResetPasswordToVerification(
                        etEmailAndNoHandPhone.text,
                        InputTypeParams.EMAIL,
                        getStringResource(R.string.label_second_step),
                        VerificationTypeParams.FORGET
                    )
                } else {
                    authNav.fromResetPasswordToVerification(
                        etEmailAndNoHandPhone.text,
                        InputTypeParams.PHONE,
                        getStringResource(R.string.label_second_step),
                        VerificationTypeParams.FORGET
                    )
                }
            }
        }
    }

    override fun initForm() {
        with(binding) {
            registerFormState(
                etEmailAndNoHandPhone.initState()
            )
            etEmailAndNoHandPhone.addRule(
                etEmailAndNoHandPhone.state,
                notEmptyRule(getStringResource(R.string.error_empty_email_no_handphone_rule)),
                ResetPasswordRule(
                    mobileNumberOnlyRule(getString(R.string.error_email_no_handphone_not_valid)),
                    emailRule(getString(R.string.error_rule_email))
                )
            )
        }
    }

    override fun onAllFormValidated() {
        binding.btnSendCode.isEnable = true
    }

    override fun onFormValidated(view: View) {
        if (view is LgnTextField) view.error = ""
    }

    override fun onFormUnvalidated(view: View, errorMessage: String) {
        if (view is LgnTextField) {
            view.error = errorMessage
            binding.btnSendCode.isEnable = false
        }
    }

    override fun isValid(): Boolean {
        val emailNoHandPhone = binding.etEmailAndNoHandPhone.state
        if (emailNoHandPhone != null) {
            if (emailNoHandPhone.value.isValid) onFormUnvalidated(
                emailNoHandPhone.value.view, getStringResource(R.string.error_empty_email_no_handphone_rule)
            )
            return emailNoHandPhone.value.isValid
        }
        return false
    }

    private val authNav: AuthNavigation by navigation { activity }
}
