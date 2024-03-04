package com.agree.ecosystem.auth.presentation.menu.register

interface RegisterDataContract {
    fun onRegisterIdle() {
        // Do Nothing
    }

    fun onRegisterLoading() {
        // Do Nothing
    }

    fun onRegisterSuccess() {
        // Do Nothing
    }

    fun onRegisterFailed(e: Throwable?) {
        // Do Nothing
    }
}
