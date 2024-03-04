package com.agree.ecosystem.auth.presentation.menu.inputphonenumber

import android.view.View
import com.agree.ecosystem.auth.R
import com.agree.ecosystem.auth.databinding.FragmentInputPhoneNumberBinding
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
import com.agree.ecosystem.core.utils.utility.validation.rules.mobileNumberOnlyRule
import com.agree.ecosystem.core.utils.utility.validation.validation
import com.agree.ui.snackbar.errorSnackBar
import com.devbase.presentation.validation.util.notEmptyRule
import com.devbase.utils.util.getStringResource
import com.telkom.legion.component.textfield.base.LgnTextField
import org.koin.androidx.viewmodel.ext.android.viewModel

class InputPhoneNumberFragment :
    AgrFormFragment<FragmentInputPhoneNumberBinding>(),
    ForgotAccountDataContract {

    override fun initUI() {
        super.initUI()
        with(binding) {
            toolbarInputPhone.navController = authNav.getNavController()
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
                    viewModel.doForgotAccountPhoneNumber(tilPhoneNumber.text) {
                        when (this) {
                            404 -> {
                                onFormUnvalidated(
                                    phoneValidation.value.view,
                                    getStringResource(R.string.error_phone_not_registered)
                                )
                            }
                            else -> {
                                errorSnackBar(it ?: "")
                            }
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
            phoneValidation
        )
    }

    override fun isValid(): Boolean {
        if (!phoneValidation.value.isValid) onFormUnvalidated(
            phoneValidation.value.view,
            if (phoneValidation.value.errorMessage == "") getString(
                R.string.error_empty_field,
                getStringResource(R.string.label_phone_number)
            ) else phoneValidation.value.errorMessage
        )
        return phoneValidation.value.isValid
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
                tilPhoneNumber.text,
                InputTypeParams.PHONE,
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
            tilPhoneNumber.addRule(
                phoneValidation,
                notEmptyRule(getStringResource(R.string.error_empty_rule_phone)),
                mobileNumberOnlyRule(getStringResource(R.string.error_rule_phone))
            )
        }
    }

    private val viewModel: ForgotAccountViewModel by viewModel()
    private val authNav: AuthNavigation by navigation { activity }
    private val phoneValidation by validation { tilPhoneNumber }
}
