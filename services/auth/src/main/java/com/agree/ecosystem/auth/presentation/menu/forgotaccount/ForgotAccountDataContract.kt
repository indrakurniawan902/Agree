package com.agree.ecosystem.auth.presentation.menu.forgotaccount

interface ForgotAccountDataContract {

    fun onForgotAccountIdle() {
        // Do Nothing
    }

    fun onForgotAccountLoading() {
        // Do Nothing
    }

    fun onForgotAccountSuccess() {
        // Do Nothing
    }

    fun onForgotAccountFailed(e: Throwable?) {
        // Do Nothing
    }
}
