package com.agree.ecosystem.auth.presentation.navigation

import android.app.Activity
import androidx.navigation.NavController
import com.agree.ecosystem.auth.R
import com.agree.ecosystem.auth.presentation.menu.changepassword.ChangePasswordFragmentDirections
import com.agree.ecosystem.auth.presentation.menu.forgotaccount.ForgotAccountFragmentDirections
import com.agree.ecosystem.auth.presentation.menu.inputemail.InputEmailFragmentDirections
import com.agree.ecosystem.auth.presentation.menu.inputphonenumber.InputPhoneNumberFragmentDirections
import com.agree.ecosystem.auth.presentation.menu.login.LoginFragmentDirections
import com.agree.ecosystem.auth.presentation.menu.onboard.OnBoardFragmentDirections
import com.agree.ecosystem.auth.presentation.menu.register.RegisterFragmentDirections
import com.agree.ecosystem.auth.presentation.menu.resetpassword.CreateNewPasswordFragmentDirections
import com.agree.ecosystem.auth.presentation.menu.resetpassword.ResetPasswordFragmentDirections
import com.agree.ecosystem.auth.presentation.menu.verification.VerificationFragmentDirections
import com.agree.ecosystem.auth.presentation.menu.verification.VerificationOTPFragmentDirections
import com.agree.ecosystem.auth.presentation.navigation.navparams.changepassword.FromParams
import com.agree.ecosystem.auth.presentation.navigation.navparams.verification.InputTypeParams
import com.agree.ecosystem.auth.presentation.navigation.navparams.verification.VerificationTypeParams
import com.agree.ecosystem.core.utils.utility.navigation.getDefaultNavOptions
import com.agree.ecosystem.utilities.presentation.base.activity.UtilitiesActivity
import com.agree.ecosystem.utilities.presentation.navigation.NavigationScreen
import com.oratakashi.viewbinding.core.tools.startActivity

class AuthNavigationImpl(
    private val activity: Activity?,
    private val nav: NavController?
) : AuthNavigation {
    override fun getNavController(): NavController? {
        return nav
    }

    override fun fromLoginToRegister() {
        runCatching {
            nav?.navigate(
//                LoginFragmentDirections.actionLoginFragmentToRegisterEmailFragment()
                LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            )
        }.onFailure {
            nav?.navigate(R.id.registerFragment, null, getDefaultNavOptions())
        }
    }

    override fun fromLoginToForgotAccount() {
        runCatching {
            nav?.navigate(
                LoginFragmentDirections.actionLoginFragmentToForgotAccountFragment()
            )
        }.onFailure {
            nav?.navigate(R.id.forgotAccountFragment, null, getDefaultNavOptions())
        }
    }

    override fun fromLoginToResetPassword() {
        kotlin.runCatching {
            nav?.navigate(
                LoginFragmentDirections.actionLoginFragmentToResetPasswordFragment()
            )
        }.onFailure {
            nav?.navigate(R.id.resetPasswordFragment, null, getDefaultNavOptions())
        }
    }

    override fun fromResetPasswordToVerification(
        inputValue: String,
        inputType: InputTypeParams,
        step: String,
        verificationType: VerificationTypeParams
    ) {

        when (inputType.name) {
            InputTypeParams.EMAIL.name -> {
                nav?.navigate(
                    ResetPasswordFragmentDirections.actionResetPasswordFragmentToVerificationEmailFragment(inputValue, step)
                )
            }
            InputTypeParams.PHONE.name -> {
                nav?.navigate(
                    ResetPasswordFragmentDirections.actionResetPasswordFragmentToVerificationOTPFragment(inputValue, step)
                )
            }
        }
    }

    override fun fromCreateNewPasswordToLogin() {
        kotlin.runCatching {
            nav?.navigate(
                CreateNewPasswordFragmentDirections.actionCreateNewFragmentToLoginFragment()
            )
        }.onFailure {
            nav?.navigate(R.id.createNewPasswordFragment, null, getDefaultNavOptions())
        }
    }

    override fun fromVerificationOTPToLogin() {
        kotlin.runCatching {
            nav?.navigate(
                VerificationOTPFragmentDirections.actionVerificationOTPFragmentToLoginFragment()
            )
        }.onFailure {
            nav?.navigate(R.id.verificationOTPFragment, null, getDefaultNavOptions())
        }
    }

    override fun fromVerificationOTPToCreateNewPassword() {
        kotlin.runCatching {
            nav?.navigate(
                VerificationOTPFragmentDirections.actionVerificationOTPFragmentToCreateNewFragment()
            )
        }.onFailure {
            nav?.navigate(R.id.verificationOTPFragment, null, getDefaultNavOptions())
        }
    }

    override fun goToPrevious() {
        nav?.navigateUp()
    }

    override fun fromLoginToMenu() {
        runCatching {
            val strActivity =
                "com.agree.ecosystem.common.presentation.base.activity.CommonActivity"
            activity?.startActivity(Class.forName(strActivity))
            activity?.finish()
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromAuthToVerification(
        inputValue: String,
        inputType: InputTypeParams,
        verificationType: VerificationTypeParams
    ) {
        when (inputType.name) {
            InputTypeParams.EMAIL.name -> {
                nav?.navigate(
                    InputEmailFragmentDirections.actionInputEmailFragmentToVerificationFragment(
                        inputValue,
                        inputType,
                        verificationType
                    )
                )
            }
            InputTypeParams.PHONE.name -> {
                nav?.navigate(
                    InputPhoneNumberFragmentDirections.actionInputPhoneNumberFragmentToVerificationFragment(
                        inputValue,
                        inputType,
                        verificationType
                    )
                )
            }
        }
    }

    override fun fromChangePasswordToVerification(
        inputValue: String,
        inputType: InputTypeParams,
        verificationType: VerificationTypeParams
    ) {
        nav?.navigate(
            ChangePasswordFragmentDirections.actionChangePasswordFragmentToVerificationFragment(
                inputValue, inputType, verificationType
            )
        )
    }

    override fun fromChangePasswordToMenu() {
        runCatching {
            activity?.finish()
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromForgotAccountToInputPhone() {
        runCatching {
            nav?.navigate(
                ForgotAccountFragmentDirections.actionForgotAccountFragmentToInputPhoneNumberFragment()
            )
        }.onFailure {
            nav?.navigate(R.id.inputPhoneNumberFragment, null, getDefaultNavOptions())
        }
    }

    override fun fromForgotAccountToInputEmail() {
        runCatching {
            nav?.navigate(
                ForgotAccountFragmentDirections.actionForgotAccountFragmentToInputEmailFragment()
            )
        }.onFailure {
            nav?.navigate(R.id.inputEmailFragment, null, getDefaultNavOptions())
        }
    }

    override fun fromRegisterToTnc() {
        runCatching {
            activity?.startActivity(UtilitiesActivity::class.java) {
                it.putExtra("screen", NavigationScreen.Tnc.name)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromRegisterToVerification(
        inputValue: String,
        verificationType: VerificationTypeParams
    ) {
        runCatching {
            nav?.navigate(
                RegisterFragmentDirections.actionRegisterFragmentToVerificationFragment(
                    inputValue,
                    InputTypeParams.PHONE,
                    verificationType
                )
            )
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromLoginToVerification(
        inputValue: String,
        verificationType: VerificationTypeParams
    ) {
        runCatching {
            nav?.navigate(
                LoginFragmentDirections.actionLoginFragmentToVerificationFragment(
                    inputValue,
                    InputTypeParams.PHONE,
                    verificationType
                )
            )
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromVerificationToChangePassword() {
        nav?.navigate(
            VerificationFragmentDirections.actionVerificationFragmentToChangePasswordFragment(
                FromParams.VERIFICATION
            )
        )
    }

    override fun fromVerificationToHome() {
        runCatching {
            val strActivity =
                "com.agree.ecosystem.common.presentation.base.activity.CommonActivity"
            activity?.startActivity(Class.forName(strActivity))
            activity?.finish()
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromOnboardToLogin() {
        runCatching {
            nav?.navigate(
                OnBoardFragmentDirections.actionOnBoardFragmentToLoginFragment()
            )
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromOnboardToRegister() {
        runCatching {
            nav?.navigate(
                OnBoardFragmentDirections.actionOnBoardFragmentToRegisterFragment()
            )
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromRegisterEmailToRegister(emailOrPhone: String, isEmail: Boolean) {
        runCatching {
//            nav?.navigate(
//                RegisterEmailFragmentDirections.actionRegisterEmailFragmentToRegisterFragment(emailOrPhone, isEmail)
//            )
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromRegisterEmailToLogin() {
        runCatching {
//            nav?.navigate(
//                RegisterEmailFragmentDirections.actionRegisterEmailFragmentToLoginFragment()
//            )
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromRegisterToVerificationOTP(phoneNumber: String, step: String) {
        runCatching {
            nav?.navigate(
                RegisterFragmentDirections.actionRegisterFragmentToVerificationOTPFragment(phoneNumber, step)
            )
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromRegisterToVerificationEmail(email: String, step: String) {
        runCatching {
            nav?.navigate(
                RegisterFragmentDirections.actionRegisterFragmentToVerificationEmailFragment(email, step)
            )
        }.onFailure {
            it.printStackTrace()
        }
    }
}
