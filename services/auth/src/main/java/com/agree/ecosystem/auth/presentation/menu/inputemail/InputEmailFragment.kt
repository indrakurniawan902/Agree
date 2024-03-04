package com.agree.ecosystem.auth.presentation.menu.inputemail

import android.view.View
import com.agree.ecosystem.auth.R
import com.agree.ecosystem.auth.databinding.FragmentInputEmailBinding
import com.agree.ecosystem.auth.presentation.menu.forgotaccount.ForgotAccountDataContract
import com.agree.ecosystem.auth.presentation.menu.forgotaccount.ForgotAccountObserver
import com.agree.ecosystem.auth.presentation.menu.forgotaccount.ForgotAccountViewModel
import com.agree.ecosystem.auth.presentation.navigation.AuthNavigation
import com.agree.ecosystem.auth.presentation.navigation.navparams.verification.InputTypeParams
import com.agree.ecosystem.auth.presentation.navigation.navparams.verification.VerificationTypeParams
import com.agree.ecosystem.core.utils.base.AgrFormFragment
import com.agree.ecosystem.core.utils.presentation.dialog.cs.CustomerServiceDialog
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.core.utils.utility.singleLinkSpan
import com.agree.ecosystem.core.utils.utility.validation.addRule
import com.agree.ecosystem.core.utils.utility.validation.validation
import com.agree.ui.snackbar.errorSnackBar
import com.devbase.presentation.validation.util.emailRule
import com.devbase.presentation.validation.util.notEmptyRule
import com.devbase.utils.util.getStringResource
import com.telkom.legion.component.textfield.base.LgnTextField
import org.koin.androidx.viewmodel.ext.android.viewModel

class InputEmailFragment : AgrFormFragment<FragmentInputEmailBinding>(), ForgotAccountDataContract {

    override fun initUI() {
        super.initUI()
        with(binding) {
            toolbarInputEmail.navController = authNav.getNavController()
            tvCallCs.text = getString(R.string.label_call_cs).singleLinkSpan(requireContext()) {
                CustomerServiceDialog().showNow(childFragmentManager, "dialog")
            }
            tvCallCs.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            btnContinue.setOnClickListener {
                if (isValid()) {
                    viewModel.doForgotAccountEmail(etEmail.text) {
                        when (this) {
                            404 -> {
                                onFormUnvalidated(
                                    emailValidation.value.view,
                                    getStringResource(
                                        R.string.error_email_not_registered
                                    )
                                )
                            }
                            else -> errorSnackBar(it ?: "")
                        }
                    }
                }
            }
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(ForgotAccountObserver(this, viewModel))
        validation.registerValidations(
            emailValidation
        )
    }

    override fun isValid(): Boolean {
        if (!emailValidation.value.isValid) onFormUnvalidated(
            emailValidation.value.view,
            if (emailValidation.value.errorMessage == "") getString(
                R.string.error_empty_field,
                getStringResource(R.string.label_email)
            ) else emailValidation.value.errorMessage

        )
        return emailValidation.value.isValid
    }

    override fun onAllFormValidated() {
        with(binding) {
            btnContinue.isEnable = true
        }
    }

    override fun onFormUnvalidated(view: View, errorMessage: String) {
        with(binding) {
            if (view is LgnTextField) {
                view.error = errorMessage
                binding.btnContinue.isEnable = true
            }
        }
    }

    override fun onFormValidated(view: View) {
        with(binding) {
            if (view is LgnTextField) view.error = ""
        }
    }

    override fun onForgotAccountIdle() {
        super.onForgotAccountIdle()
        with(binding) {
            btnContinue.isLoading = false
        }
    }

    override fun onForgotAccountLoading() {
        super.onForgotAccountLoading()
        with(binding) {
            btnContinue.isLoading = true
        }
    }

    override fun onForgotAccountSuccess() {
        super.onForgotAccountSuccess()
        with(binding) {
            btnContinue.isLoading = false
            authNav.fromAuthToVerification(
                etEmail.text,
                InputTypeParams.EMAIL,
                VerificationTypeParams.FORGET
            )
        }
    }

    override fun onForgotAccountFailed(e: Throwable?) {
        super.onForgotAccountFailed(e)
        with(binding) {
            btnContinue.isLoading = false
        }
    }

    override fun initForm() {
        with(binding) {
            etEmail.addRule(
                emailValidation,
                notEmptyRule(getStringResource(R.string.error_empty_email_rule)),
                emailRule(getStringResource(R.string.error_rule_email))
            )
        }
    }

    private val viewModel: ForgotAccountViewModel by viewModel()
    private val authNav: AuthNavigation by navigation { activity }
    private val emailValidation by validation { etEmail }
}
