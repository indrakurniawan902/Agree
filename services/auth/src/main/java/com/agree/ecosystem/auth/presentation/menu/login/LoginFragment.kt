package com.agree.ecosystem.auth.presentation.menu.login

import android.text.method.LinkMovementMethod
import android.view.View
import com.agree.ecosystem.auth.R
import com.agree.ecosystem.auth.databinding.FragmentLoginBinding
import com.agree.ecosystem.auth.domain.reqres.model.login.Engine
import com.agree.ecosystem.auth.domain.reqres.model.login.Login
import com.agree.ecosystem.auth.presentation.navigation.AuthNavigation
import com.agree.ecosystem.auth.presentation.navigation.navparams.verification.VerificationTypeParams
import com.agree.ecosystem.core.analytics.data.model.auth.LoginAnalyticsItem
import com.agree.ecosystem.core.utils.base.AgrFormFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.defaultErrorHandling
import com.agree.ecosystem.core.utils.utility.eventbus.AppEvent
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.core.utils.utility.validation.addRule
import com.agree.ecosystem.core.utils.utility.validation.initState
import com.agree.ecosystem.core.utils.utility.validation.rules.minLengthRule
import com.agree.ecosystem.core.utils.utility.validation.rules.mobileNumberOnlyRule
import com.agree.ecosystem.core.utils.utility.validation.rules.usecase.LoginRule
import com.agree.ecosystem.core.utils.utility.validation.rules.usernameRule
import com.agree.ecosystem.core.utils.utility.validation.update
import com.agree.ui.snackbar.normalSnackBar
import com.devbase.presentation.validation.util.notEmptyRule
import com.telkom.legion.component.textfield.base.LgnTextField
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment :
    AgrFormFragment<FragmentLoginBinding>(),
    LoginDataContract {

    override fun initUI() {
        super.initUI()
        with(binding) {
            if (prefs.isRemember) etUsername.text = prefs.userName
            cbLogin.isChecked = prefs.isRemember
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            btnLogin.setOnClickListener {
                if (isValid()) {
                    doLogin()
                }
            }

            tvRegister.apply {
                movementMethod = LinkMovementMethod.getInstance()
                setOnClickListener { authNav.fromLoginToRegister() }
            }
            tvForgotPassword.apply {
                movementMethod = LinkMovementMethod.getInstance()
                setOnClickListener { authNav.fromLoginToForgotAccount() }
            }
            imgBack.setOnClickListener {
                authNav.goToPrevious()
            }
        }
    }

    override fun doLogin() {
        with(binding) {
            viewModel.doLogin(
                etUsername.text,
                etPassword.text
            ) {
                when (this) {
                    404 -> etUsername.state?.update(
                        etUsername, false, it.orEmpty()
                    )
                    401 -> etPassword.state?.update(
                        etPassword, false, it.orEmpty()
                    )
                    else -> defaultErrorHandling(this, it.orEmpty()) {
                        doLogin()
                    }
                }
            }
        }
    }

    override fun initForm() {
        with(binding) {
            registerFormState(
                etUsername.initState(),
                etPassword.initState()
            )
            etUsername.addRule(
                etUsername.state,
                notEmptyRule(
                    getString(
                        R.string.error_empty_field,
                        getString(R.string.label_username_or_phone)
                    )
                ),
                LoginRule(
                    usernameRule(getString(R.string.error_username_rule)),
                    mobileNumberOnlyRule(getString(R.string.error_rule_phone))
                )
            )
            etPassword.addRule(
                etPassword.state,
                minLengthRule(8, getString(R.string.error_rule_password_8chars)),
                notEmptyRule(
                    getString(
                        R.string.error_empty_field,
                        getString(R.string.label_password)
                    )
                )
            )
        }
    }

    override fun onAllFormValidated() {
        with(binding) {
            btnLogin.isEnable = true
        }
    }

    override fun onFormUnvalidated(view: View, errorMessage: String) {
        super.onFormUnvalidated(view, errorMessage)
        with(binding) {
            (view as LgnTextField).error = errorMessage
            btnLogin.isEnable = true
        }
    }

    override fun onFormValidated(view: View) {
        (view as LgnTextField).error = ""
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(LoginObserver(this, viewModel))
    }

    override fun onLoginIdle() {
        super.onLoginIdle()
        with(binding) {
            btnLogin.isLoading = false
        }
    }

    override fun onLoginLoading() {
        super.onLoginLoading()
        with(binding) {
            btnLogin.isLoading = true
        }
    }

    override fun onLoginSuccess(data: Pair<Login, Engine>) {
        super.onLoginSuccess(data)
        with(binding) {
            btnLogin.isLoading = false
            if (data.first.accessToken.isNotEmpty() && data.first.refreshToken.isNotEmpty()) {
                prefs.accessToken = data.first.accessToken
                prefs.refreshToken = data.first.refreshToken
                prefs.isRemember = cbLogin.isChecked
                prefs.userName = data.first.username
                prefs.userId = data.first.id
                prefs.phoneNumber = data.first.phoneNumber
                prefs.accessTokenEngine = data.second.accessToken
                // Analytics Tracker
                viewModel.trackLogin(LoginAnalyticsItem(data.first.id, data.first.username, data.first.accessToken))
                authNav.fromLoginToMenu()
            } else {
                normalSnackBar(getString(R.string.label_account_not_verified))
                broadcastEvent { AppEvent.CHANGE_STATUS_BAR_WHITE }
                authNav.fromLoginToVerification(
                    data.first.phoneNumber, VerificationTypeParams.REGISTER
                )
            }
        }
    }

    override fun onLoginFailed(e: Throwable?) {
        super.onLoginFailed(e)
        with(binding) {
            btnLogin.isLoading = false
        }
    }

    override fun isValid(): Boolean {
        with(binding) {
            val usernameValidation = etUsername.state
            val passwordValidation = etPassword.state
            if (usernameValidation != null && passwordValidation != null) {
                if (!usernameValidation.value.isValid) onFormUnvalidated(
                    usernameValidation.value.view,
                    getString(
                        R.string.error_empty_field,
                        getString(R.string.label_username_or_phone)
                    )
                )
                if (!passwordValidation.value.isValid) onFormUnvalidated(
                    passwordValidation.value.view,
                    getString(
                        R.string.error_empty_field,
                        getString(R.string.label_password)
                    )
                )
                return usernameValidation.value.isValid && passwordValidation.value.isValid
            }
        }
        return false
    }

    private val viewModel: LoginViewModel by viewModel()
    private val prefs: AgrPrefUsecase by inject()

    private val authNav: AuthNavigation by navigation { activity }
}
