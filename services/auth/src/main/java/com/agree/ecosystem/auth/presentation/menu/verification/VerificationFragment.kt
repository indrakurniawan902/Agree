package com.agree.ecosystem.auth.presentation.menu.verification

import android.os.CountDownTimer
import android.view.View
import androidx.navigation.fragment.navArgs
import com.agree.ecosystem.auth.R
import com.agree.ecosystem.auth.databinding.FragmentVerificationBinding
import com.agree.ecosystem.auth.domain.reqres.model.login.Login
import com.agree.ecosystem.auth.presentation.navigation.AuthNavigation
import com.agree.ecosystem.auth.presentation.navigation.navparams.verification.InputTypeParams
import com.agree.ecosystem.auth.presentation.navigation.navparams.verification.VerificationTypeParams
import com.agree.ecosystem.core.analytics.data.model.auth.LoginAnalyticsItem
import com.agree.ecosystem.core.utils.base.AgrFormFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.domain.reqres.model.DataSpannable
import com.agree.ecosystem.core.utils.presentation.dialog.cs.CustomerServiceDialog
import com.agree.ecosystem.core.utils.utility.DialogUtils
import com.agree.ecosystem.core.utils.utility.eventbus.AppEvent
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.core.utils.utility.singleLinkSpan
import com.agree.ui.UiColors
import com.agree.ui.UiKitAttrs
import com.agree.ui.snackbar.errorSnackBar
import com.agree.ui.snackbar.normalSnackBar
import com.devbase.utils.util.getColorResource
import com.devbase.utils.util.getStringResource
import com.oratakashi.viewbinding.core.tools.finish
import com.oratakashi.viewbinding.core.tools.gone
import com.oratakashi.viewbinding.core.tools.onTextChanged
import com.oratakashi.viewbinding.core.tools.toast
import com.oratakashi.viewbinding.core.tools.visible
import com.telkom.legion.component.utility.extension.requiredColor
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class VerificationFragment :
    AgrFormFragment<FragmentVerificationBinding>(),
    VerificationDataContract,
    OtpDataContract {

    override fun initUI() {
        super.initUI()
        countDown()
        with(binding) {
            tilCode5.gone()
            tilCode6.gone()
            tvErrorCode.gone()
            toolbar.setBackButtonOnClick {
                requireActivity().onBackPressed()
            }
            tvCallCs.text = getString(R.string.label_call_cs).singleLinkSpan(requireContext()) {
                CustomerServiceDialog().showNow(childFragmentManager, "dialog")
            }
            tvCallCs.movementMethod = android.text.method.LinkMovementMethod.getInstance()

            etCode1.onTextChanged {
                if (etCode1.text.toString().length == 1) {
                    etCode2.requestFocus()
                }
            }
            etCode2.onTextChanged {
                if (etCode2.text.toString().length == 1) {
                    etCode3.requestFocus()
                }
            }
            etCode3.onTextChanged {
                if (etCode3.text.toString().length == 1) {
                    etCode4.requestFocus()
                }
            }
            etCode4.onTextChanged {
                buttonEnable = etCode4.text.toString().length == 1
            }

            etCode2.setOnKeyListener(deleteListener)
            etCode3.setOnKeyListener(deleteListener)
            etCode4.setOnKeyListener(deleteListener)
            val descStr = StringBuilder().apply {
                append(getStringResource(R.string.label_verification_description))
                append(" ")
                append("<a>${args.inputValue}</a>")
            }
            tvVerificationDescription.text = descStr.toString().singleLinkSpan(requireContext())
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            btnVerification.setOnClickListener {
                if (!buttonEnable) {
                    tvErrorCode.text = getStringResource(R.string.label_code_not_empty)
                    tvErrorCode.visible()
                    return@setOnClickListener
                }

                tvErrorCode.gone()

                if (args.inputType == InputTypeParams.EMAIL) {
                    doVerifyOptionEmail()
                } else {
                    doVerifyOptionPhone()
                }
            }
            tvSendAgain.setOnClickListener {
                if (clickable) {
                    countDown()
                    if (args.inputValue.contains("@"))
                        viewModelOtp.doGetOtpByEmail(args.inputValue) {
                            errorSnackBar(it ?: "")
                        } else
                        viewModelOtp.doGetOtp(args.inputValue) {
                            errorSnackBar(it ?: "")
                        }
                }
            }
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(
            VerificationObserver(this, viewModelVerification),
            OtpObserver(this, viewModelOtp)
        )
        validation.registerValidations()
    }

    override fun doVerifyOptionEmail() {
        viewModelVerification.doVerifyOptionEmail(
            args.inputValue,
            combineOtp()
        ) {
            when (this) {
                400 -> {
                    setFieldError(it)
                }

                else -> errorSnackBar(it ?: "")
            }
        }
    }

    override fun doVerifyOptionPhone() {
        viewModelVerification.doVerifyOption(
            args.inputValue,
            combineOtp()
        ) {
            when (this) {
                400 -> {
                    setFieldError(it)
                }

                else -> errorSnackBar(it ?: "")
            }
        }
    }

    private val deleteListener: View.OnKeyListener by lazy {
        with(binding) {
            android.view.View.OnKeyListener { view, keyCode, event ->
                val isDeleteClicked =
                    event.action == android.view.KeyEvent.ACTION_DOWN && keyCode == android.view.KeyEvent.KEYCODE_DEL
                when (view) {
                    etCode6 -> {
                        if (isDeleteClicked && etCode6.text.toString().isBlank()) {
                            etCode5.requestFocus()
                        }
                    }

                    etCode5 -> {
                        if (isDeleteClicked && etCode5.text.toString().isBlank()) {
                            etCode4.requestFocus()
                        }
                    }

                    etCode4 -> {
                        if (isDeleteClicked && etCode4.text.toString().isBlank()) {
                            etCode3.requestFocus()
                        }
                    }

                    etCode3 -> {
                        if (isDeleteClicked && etCode3.text.toString().isBlank()) {
                            etCode2.requestFocus()
                        }
                    }

                    etCode2 -> {
                        if (isDeleteClicked && etCode2.text.toString().isBlank()) {
                            etCode1.requestFocus()
                        }
                    }
                }
                return@OnKeyListener false
            }
        }
    }

    private fun countDown() = object : CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            with(binding) {
                clickable = false
                tvSendTimer.text = "(${millisUntilFinished / 1000}s)"
                tvSendAgain.setTextColor(getColorResource(UiColors.tertiary_600))
            }
        }

        override fun onFinish() {
            with(binding) {
                tvSendTimer.text = ""
                tvSendAgain.setTextColor(requireContext().requiredColor(UiKitAttrs.colorPrimary))
                clickable = true
            }
        }
    }.start()

    private fun createSpan(firstChar: Int, lastChar: Int): DataSpannable {
        return DataSpannable(
            listOf(
                DataSpannable.ListSpinnable(firstChar, lastChar) {
                }
            )
        )
    }

    private fun combineOtp(): String {
        var otp = ""
        with(binding) {
            otp += etCode1.text.toString()
            otp += etCode2.text.toString()
            otp += etCode3.text.toString()
            otp += etCode4.text.toString()
        }
        return otp
    }

    private fun setFieldError(message: String? = null) {
        with(binding) {
            tilCode1.error = " "
            tilCode1.setErrorIconDrawable(0)
            tilCode2.error = " "
            tilCode2.setErrorIconDrawable(0)
            tilCode3.error = " "
            tilCode3.setErrorIconDrawable(0)
            tilCode4.error = " "
            tilCode4.setErrorIconDrawable(0)
            tilCode5.error = " "
            tilCode5.setErrorIconDrawable(0)
            tilCode6.error = " "
            tilCode6.setErrorIconDrawable(0)
            tvErrorCode.text = message ?: getStringResource(R.string.label_error_otp)
            tvErrorCode.visible()
        }
    }

    override fun onPause() {
        countDown().cancel()
        super.onPause()
    }

    override fun onAllFormValidated() {
        // Do Nothing
    }

    override fun onFormUnvalidated(view: View, errorMessage: String) {
        // Do Nothing
    }

    override fun onFormValidated(view: View) {
        // Do Nothing
    }

    override fun initForm() {
        // Do Nothing
    }

    /**
     * When Verify Type is :
     * * Change Password : Main Nav will Navigate Up because to navigate into
     *                     verification Fragment use pop up exclusive,
     *                     and clear Change Password Fragment from backstack
     * * Forget : Will Show Dialog to Change password or not, if not will popup exclusive into
     *            menu fragment, but if change password will pup exclusive into change password
     * * Register : Will Pop Up Exclusive into Menu Fragment
     */
    override fun onVerificationSuccess(data: Login) {
        super.onVerificationSuccess(data)
        when (args.verificationType) {
            VerificationTypeParams.CHANGE_PASSWORD_MENU -> {
                toast(getString(R.string.label_succes_change_password))
                finish()
            }

            VerificationTypeParams.FORGET -> {
                if (data.accessToken.isNotEmpty() && data.refreshToken.isNotEmpty()) {
                    prefs.accessToken = data.accessToken
                    prefs.refreshToken = data.refreshToken
                    prefs.userName = data.username
                    prefs.userId = data.id
                    prefs.phoneNumber = data.phoneNumber

                    // Analytics Tracker
                    viewModelVerification.trackLogin(
                        LoginAnalyticsItem(
                            data.id,
                            data.username,
                            data.accessToken
                        )
                    )

                    DialogUtils.showCustomDialog(
                        context = requireContext(),
                        title = getStringResource(R.string.label_changed_password),
                        message = getStringResource(R.string.label_changed_password_message),
                        positiveAction = Pair(getStringResource(R.string.label_change)) {
                            authNav.fromVerificationToChangePassword()
                        },
                        negativeAction = Pair(getStringResource(R.string.label_later)) {
                            authNav.fromVerificationToHome()
                        },
                        autoDismiss = false
                    )
                } else {
                    requireActivity()
                    normalSnackBar(getString(R.string.label_account_not_verified))
                    broadcastEvent { AppEvent.CHANGE_STATUS_BAR_WHITE }
                }
            }

            VerificationTypeParams.REGISTER -> {
                if (data.accessToken.isNotEmpty() && data.refreshToken.isNotEmpty()) {
                    prefs.accessToken = data.accessToken
                    prefs.refreshToken = data.refreshToken
                    prefs.userName = data.username
                    prefs.userId = data.id

                    // Analytics Tracker
                    viewModelVerification.trackLogin(
                        LoginAnalyticsItem(
                            data.id,
                            data.username,
                            data.accessToken
                        )
                    )

                    authNav.fromVerificationToHome()
                } else {
                    normalSnackBar(getString(R.string.label_account_not_verified))
                    broadcastEvent { AppEvent.CHANGE_STATUS_BAR_WHITE }
                }
            }

            VerificationTypeParams.CHANGE_PASSWORD_FORGET -> {
                authNav.fromVerificationToHome()
            }
        }
    }

    override fun onOtpSuccess() {
        super.onOtpSuccess()
        toast("Berhasil mengirimkan ulang kode verifikasi")
    }

    private val viewModelVerification: VerificationViewModel by viewModel()
    private val viewModelOtp: OtpViewModel by viewModel()
    private val authNav: AuthNavigation by navigation { activity }

    private val prefs: AgrPrefUsecase by inject()

    private var clickable = true
    private var buttonEnable = false
    private val args: VerificationFragmentArgs by navArgs()
    private var isCodeCorrect = false
}
