package com.agree.ecosystem.auth.presentation.menu.register

import android.text.method.LinkMovementMethod
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.auth.R
import com.agree.ecosystem.auth.databinding.FragmentRegisterBinding
import com.agree.ecosystem.auth.presentation.navigation.AuthNavigation
import com.agree.ecosystem.auth.presentation.navigation.navparams.verification.VerificationTypeParams
import com.agree.ecosystem.core.utils.base.AgrFormFragment
import com.agree.ecosystem.core.utils.domain.reqres.model.ValidationModel
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.core.utils.utility.singleLinkSpan
import com.agree.ecosystem.core.utils.utility.validation.addRule
import com.agree.ecosystem.core.utils.utility.validation.initState
import com.agree.ecosystem.core.utils.utility.validation.rules.mobileNumberOnlyRule
import com.agree.ecosystem.core.utils.utility.validation.rules.usernameRule
import com.agree.ecosystem.core.utils.utility.validation.update
import com.agree.ui.snackbar.errorSnackBar
import com.devbase.presentation.validation.rules.CheckedRule
import com.devbase.presentation.validation.rules.RegexRule
import com.devbase.presentation.validation.util.alphaOnlyRule
import com.devbase.presentation.validation.util.emailRule
import com.devbase.presentation.validation.util.notEmptyRule
import com.devbase.presentation.validation.util.sameValueRule
import com.devbase.utils.util.getStringResource
import com.oratakashi.viewbinding.core.tools.toast
import com.telkom.legion.component.textfield.base.LgnTextField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : AgrFormFragment<FragmentRegisterBinding>(), RegisterDataContract {

    private var isPasswordFocuses = false
    private var isRegisterSuccess = false

    override fun initUI() {
        super.initUI()
        with(binding) {
            toolbar.navController = authNav.getNavController()
            tvLogin.text =
                getString(R.string.label_command_login).singleLinkSpan(requireContext()) {
                    authNav.goToPrevious()
                }
            tvLogin.movementMethod = LinkMovementMethod.getInstance()

//            cbTnc.text = getString(R.string.label_tnc_checkbox).customLinkSpan(
//                requireContext(),
//                createSpan(49, 67)
//            )
            cbTnc.text = getString(R.string.label_tnc_checkbox).singleLinkSpan(requireContext()) {
                authNav.fromRegisterToTnc()
            }
            cbTnc.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            btnRegister.setOnClickListener {
                if (isValid()) {
                    viewModel.doRegister(
                        etFullname.text,
                        etUsername.text,
                        etPhoneNumber.text,
                        etEmail.text,
                        etPassword.text,
                        etPasswordConfirmation.text
                    ) {
                        when (this) {
                            409 -> {
                                val list = it.toString().split(" ")
                                when (list[0]) {
                                    getStringResource(R.string.label_user_check) -> etUsername.state?.update(
                                        etUsername,
                                        false,
                                        getStringResource(R.string.error_username_registered)
                                    )
                                    getStringResource(R.string.label_email_check) -> etEmail.state?.update(
                                        etEmail,
                                        false,
                                        getStringResource(R.string.error_email_registered)
                                    )
                                    getStringResource(R.string.label_phone_check) -> etPhoneNumber.state?.update(
                                        etPhoneNumber,
                                        false,
                                        getStringResource(R.string.error_phone_registered)
                                    )
                                }
                            }
                            else -> errorSnackBar(it ?: "")
                        }
                    }
                }
            }
        }
    }

    override fun isValid(): Boolean {
        with(binding) {
            val nameValidation = etFullname.state
            val usernameValidation = etUsername.state
            val phoneNumberValidation = etPhoneNumber.state
            val passwordValidation = etPassword.state
            val passwordConfirmValidation = etPasswordConfirmation.state
            val tncValidation = cbTnc.state

            val stateList: List<MutableStateFlow<ValidationModel>?> = listOf(
                nameValidation,
                usernameValidation,
                phoneNumberValidation,
                passwordValidation,
                passwordConfirmValidation,
                tncValidation
            )

            if (stateList.contains(null)) {
                return false
            }

            lifecycleScope.launch {
                stateList.filter { it?.value?.isValid != true }.onEach { state ->
                    state?.let {
                        when (it.value.view.id) {
                            R.id.etFullname -> {
                                onFormUnvalidated(
                                    it.value.view,
                                    getStringResource(R.string.error_empty_name_rule)
                                )
                            }
                            R.id.etUsername -> {
                                onFormUnvalidated(
                                    it.value.view,
                                    getString(
                                        R.string.error_empty_field_must_filled,
                                        getStringResource(R.string.label_username)
                                    )
                                )
                            }
                            R.id.etPhoneNumber -> {
                                onFormUnvalidated(
                                    it.value.view,
                                    getString(
                                        R.string.error_empty_field_must_filled,
                                        getStringResource(R.string.label_telephone)
                                    )
                                )
                            }
                            R.id.etPassword -> {
                                onFormUnvalidated(
                                    it.value.view,
                                    getString(
                                        R.string.error_empty_field_must_filled,
                                        getStringResource(R.string.label_password)
                                    )
                                )
                            }
                            R.id.etPasswordConfirmation -> {
                                onFormUnvalidated(
                                    it.value.view,
                                    getString(
                                        R.string.error_empty_field_must_filled,
                                        getStringResource(R.string.label_password_confirmation)
                                    )
                                )
                            }
                            R.id.cbTnc -> {
                                toast(getStringResource(R.string.error_user_agreement))
                            }
                        }
                    }
                }
            }

            return !stateList.any { it?.value?.isValid == false }
        }
    }

    override fun onAllFormValidated() {
        with(binding) {
            btnRegister.isEnable = true
        }
    }

    override fun onFormUnvalidated(view: View, errorMessage: String) {
        super.onFormUnvalidated(view, errorMessage)
        with(binding) {
            if (view is LgnTextField) {
                view.error = errorMessage
                btnRegister.isEnable = true
            }
        }
    }

    override fun onFormValidated(view: View) {
        with(binding) {

            if (view.id == etPassword.id && isPasswordFocuses) {
                val oldText = etPasswordConfirmation.text
                etPasswordConfirmation.text = oldText
                etPasswordConfirmation.editText?.setSelection(etPasswordConfirmation.text.length)
            }

            if (view is LgnTextField) view.error = ""
        }
    }

    override fun initForm() {
        with(binding) {
            registerFormState(
                etFullname.initState(),
                etUsername.initState(),
                etEmail.initState(),
                etPhoneNumber.initState(),
                etPassword.initState(),
                etPasswordConfirmation.initState(),
                cbTnc.initState()
            )

            etFullname.addRule(
                etFullname.state,
                notEmptyRule(getStringResource(R.string.error_empty_name_rule)),
                alphaOnlyRule(getStringResource(R.string.error_alpha_only_name_rule))
            )
            etUsername.addRule(
                etUsername.state,
                usernameRule(getStringResource(R.string.error_username_rule)),
                notEmptyRule(getStringResource(R.string.error_empty_username_rule))
            )
            etEmail.addRule(
                etEmail.state,
                emailRule(getStringResource(R.string.error_email_rule))
            )
            etPhoneNumber.addRule(
                etPhoneNumber.state,
                notEmptyRule(getStringResource(R.string.error_empty_rule_phone)),
                mobileNumberOnlyRule(getStringResource(R.string.error_rule_phone))
            )

            etPassword.setOnFocusChangeListener { _, hasFocus ->
                isPasswordFocuses = hasFocus || etPasswordConfirmation.hasFocus()
            }
            etPasswordConfirmation.setOnFocusChangeListener { _, hasFocus ->
                isPasswordFocuses = hasFocus || etPassword.hasFocus()
            }

            etPassword.addRule(
                etPassword.state,
                RegexRule("^.{8,}\$", getStringResource(R.string.error_regex_rule)),
                notEmptyRule(getStringResource(R.string.error_password_empty))
            )
            etPasswordConfirmation.addRule(
                etPasswordConfirmation.state,
                notEmptyRule(getStringResource(R.string.error_password_confirmation_empty)),
                sameValueRule({ etPassword.text }, getStringResource(R.string.error_same_value))
            )

            cbTnc.addRule(
                cbTnc.state,
                CheckedRule(getString(R.string.error_user_agreement))
            )
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(RegisterObserver(this, viewModel))
    }

    override fun onRegisterIdle() {
        super.onRegisterIdle()
        with(binding) {
            btnRegister.isLoading = false
        }
    }

    override fun onRegisterLoading() {
        super.onRegisterLoading()
        with(binding) {
            btnRegister.isLoading = true
        }
    }

    override fun onRegisterSuccess() {
        super.onRegisterSuccess()
        with(binding) {
            btnRegister.isLoading = false
            authNav.fromRegisterToVerification(
                etPhoneNumber.text,
                VerificationTypeParams.REGISTER
            )
        }
    }

    override fun onRegisterFailed(e: Throwable?) {
        super.onRegisterFailed(e)
        with(binding) {
            btnRegister.isLoading = false
        }
    }

    private val viewModel: RegisterViewModel by viewModel()
    private val authNav: AuthNavigation by navigation { activity }
}
