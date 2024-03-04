package com.agree.ecosystem.auth.presentation.menu.verification

interface OtpDataContract {
    fun onOtpIdle() {
        // Do Nothing
    }

    fun onOtpLoading() {
        // Do Nothing
    }

    fun onOtpSuccess() {
        // Do Nothing
    }

    fun onOtpFailed(e: Throwable?) {
        // Do Nothing
    }
}
