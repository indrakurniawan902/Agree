package com.agree.ecosystem.auth.presentation.navigation

import androidx.navigation.NavController
import com.agree.ecosystem.auth.presentation.navigation.navparams.verification.InputTypeParams
import com.agree.ecosystem.auth.presentation.navigation.navparams.verification.VerificationTypeParams

interface AuthNavigation {
    fun getNavController(): NavController?
    fun fromLoginToRegister()
    fun fromLoginToForgotAccount()
    fun fromLoginToResetPassword()
    fun fromResetPasswordToVerification(
        inputValue: String,
        inputType: InputTypeParams,
        step: String,
        verificationType: VerificationTypeParams
    )
    fun fromCreateNewPasswordToLogin()
    fun fromVerificationOTPToLogin()
    fun fromVerificationOTPToCreateNewPassword()

    fun goToPrevious()

    fun fromLoginToMenu()
    fun fromAuthToVerification(
        inputValue: String,
        inputType: InputTypeParams,
        verificationType: VerificationTypeParams
    )

    fun fromChangePasswordToVerification(
        inputValue: String,
        inputType: InputTypeParams,
        verificationType: VerificationTypeParams
    )

    fun fromChangePasswordToMenu()

    fun fromForgotAccountToInputPhone()
    fun fromForgotAccountToInputEmail()

    fun fromRegisterToTnc()

    fun fromRegisterToVerification(
        inputValue: String,
        verificationType: VerificationTypeParams
    )

    fun fromLoginToVerification(
        inputValue: String,
        verificationType: VerificationTypeParams
    )

    fun fromVerificationToChangePassword()
    fun fromVerificationToHome()

    fun fromOnboardToLogin()
    fun fromOnboardToRegister()

    fun fromRegisterEmailToRegister(emailOrPhone: String, isEmail: Boolean)
    fun fromRegisterEmailToLogin()
    fun fromRegisterToVerificationOTP(phoneNumber: String, step: String)
    fun fromRegisterToVerificationEmail(email: String, step: String)
}
