package com.agree.ecosystem.auth.presentation.menu.verification

import com.agree.ecosystem.auth.domain.reqres.model.login.Login

interface VerificationDataContract {

    fun doVerifyOptionEmail()
    fun doVerifyOptionPhone()

    fun onVerificationIdle() {
        // Do Nothing
    }

    fun onVerificationLoading() {
        // Do Nothing
    }

    fun onVerificationSuccess(data: Login) {
        // Do Nothing
    }

    fun onVerificationFailed(e: Throwable?) {
        // Do Nothing
    }
}
