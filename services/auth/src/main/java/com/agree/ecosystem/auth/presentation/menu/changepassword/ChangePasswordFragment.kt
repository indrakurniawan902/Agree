package com.agree.ecosystem.auth.presentation.menu.changepassword

import android.view.View
import androidx.navigation.fragment.navArgs
import com.agree.ecosystem.auth.R
import com.agree.ecosystem.auth.databinding.FragmentChangePasswordBinding
import com.agree.ecosystem.auth.presentation.navigation.AuthNavigation
import com.agree.ecosystem.auth.presentation.navigation.navparams.changepassword.FromParams
import com.agree.ecosystem.auth.presentation.navigation.navparams.verification.InputTypeParams
import com.agree.ecosystem.auth.presentation.navigation.navparams.verification.VerificationTypeParams
import com.agree.ecosystem.core.utils.base.AgrFormFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.presentation.dialog.cs.CustomerServiceDialog
import com.agree.ecosystem.core.utils.utility.DialogUtils
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.core.utils.utility.singleLinkSpan
import com.agree.ecosystem.core.utils.utility.validation.addRule
import com.agree.ecosystem.core.utils.utility.validation.validation
import com.agree.ecosystem.users.data.reqres.model.profile.ChangePasswordBodyPost
import com.agree.ui.snackbar.errorSnackBar
import com.devbase.presentation.validation.rules.RegexRule
import com.devbase.presentation.validation.util.notEmptyRule
import com.devbase.presentation.validation.util.sameValueRule
import com.devbase.utils.util.getStringResource
import com.oratakashi.viewbinding.core.tools.finish
import com.oratakashi.viewbinding.core.tools.toast
import com.telkom.legion.component.textfield.base.LgnTextField
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePasswordFragment :
    AgrFormFragment<FragmentChangePasswordBinding>(),
    ChangePasswordDataContract {

    private var isPasswordFocuses = false

    private val authNav: AuthNavigation by navigation { activity }
    private val args: ChangePasswordFragmentArgs by navArgs()
    private val viewModel: ChangePasswordViewModel by viewModel()
    private val prefs: AgrPrefUsecase by inject()

    override fun initUI() {
        super.initUI()
        with(binding) {
            toolbar.navController = authNav.getNavController()
            toolbar.setBackButtonOnClick {
                if (args.from == FromParams.VERIFICATION) {
                    authNav.fromChangePasswordToMenu()
                } else {
                    finish()
                }
            }
            tvCallCs.text = getString(R.string.label_call_cs).singleLinkSpan(requireContext()) {
                CustomerServiceDialog().showNow(childFragmentManager, "dialog")
            }
            tvCallCs.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            btnSave.setOnClickListener {
                if (isValid()) {
                    DialogUtils.showCustomDialog(
                        context = requireContext(),
                        title = "",
                        message = getString(R.string.label_change_password_dialog),
                        positiveAction = Pair(getString(R.string.action_yes)) {
                            viewModel.changePassword(
                                ChangePasswordBodyPost(prefs.userId, tilNewPassword.text)
                            ) {
                                when (this) {
                                    400 -> {
                                        toast(it ?: "")
                                    }
                                    else -> errorSnackBar(it ?: "")
                                }
                                btnSave.isLoading = false
                            }
                        },
                        negativeAction = Pair(getString(R.string.action_cancel), null),
                        autoDismiss = false
                    )
                }
            }
        }
    }

    override fun isValid(): Boolean {
        var isAllValid = true

        if (!passwordValidation.value.isValid) onFormUnvalidated(
            passwordValidation.value.view,
            if (passwordValidation.value.errorMessage == "") getString(
                R.string.error_empty_field,
                getStringResource(R.string.label_password)
            ) else passwordValidation.value.errorMessage
        )
        isAllValid = passwordValidation.value.isValid && isAllValid

        if (!confirmPasswordValidation.value.isValid) onFormUnvalidated(
            confirmPasswordValidation.value.view,
            if (confirmPasswordValidation.value.errorMessage == "") getString(
                R.string.error_empty_field,
                getStringResource(R.string.label_password_confirmation)
            ) else confirmPasswordValidation.value.errorMessage
        )
        isAllValid = confirmPasswordValidation.value.isValid && isAllValid

        return isAllValid
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(
            ChangePasswordObserver(this, viewModel)
        )
        validation.registerValidations(
            passwordValidation,
            confirmPasswordValidation
        )
    }

    override fun onAllFormValidated() {
        with(binding) {
            btnSave.isEnable = true
        }
    }

    override fun onFormUnvalidated(view: View, errorMessage: String) {
        with(binding) {
            if (view is LgnTextField) {
                view.error = errorMessage
                binding.btnSave.isEnable = true
            }
        }
    }

    override fun onFormValidated(view: View) {
        with(binding) {

            if (view.id == tilNewPassword.id && isPasswordFocuses) {
                tilPasswordConfirmation.editText?.setSelection(tilPasswordConfirmation.text.length)
            }
            if (view is LgnTextField) view.error = ""
        }
    }

    override fun initForm() {
        with(binding) {
            tilNewPassword.addRule(
                passwordValidation,
                RegexRule("^.{8,}\$", getStringResource(R.string.error_regex_rule)),
                notEmptyRule(getStringResource(R.string.error_password_empty))
            )
            tilPasswordConfirmation.addRule(
                confirmPasswordValidation,
                notEmptyRule(getStringResource(R.string.error_password_confirmation_empty)),
                sameValueRule({ tilNewPassword.text }, getStringResource(R.string.error_same_value))
            )
        }
    }

    private val passwordValidation by validation { tilNewPassword }
    private val confirmPasswordValidation by validation { tilPasswordConfirmation }

    override fun onLoading() {
        with(binding) {
            btnSave.isLoading = true
        }
    }

    override fun onChangePasswordSuccess() {
        with(binding) {
            btnSave.isLoading = true
            if (args.from == FromParams.MENU) {
                authNav.fromChangePasswordToVerification(
                    prefs.phoneNumber,
                    InputTypeParams.PHONE,
                    VerificationTypeParams.CHANGE_PASSWORD_MENU
                )
            } else {
                authNav.fromChangePasswordToVerification(
                    prefs.phoneNumber,
                    InputTypeParams.PHONE,
                    VerificationTypeParams.CHANGE_PASSWORD_FORGET
                )
            }
        }
    }

    override fun onChangePasswordFailed(e: Throwable?) {
        with(binding) {
            btnSave.isLoading = false
        }
    }
}
