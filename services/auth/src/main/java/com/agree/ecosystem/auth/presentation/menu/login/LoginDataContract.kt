package com.agree.ecosystem.auth.presentation.menu.login

import com.agree.ecosystem.auth.domain.reqres.model.login.Engine
import com.agree.ecosystem.auth.domain.reqres.model.login.Login

interface LoginDataContract {

    fun doLogin() {
        // Do Nothing
    }

    fun onLoginIdle() {
        // Do Nothing
    }

    fun onLoginLoading() {
        // Do Nothing
    }

    fun onLoginSuccess(data: Pair<Login, Engine>) {
        // Do Nothing
    }

    fun onLoginFailed(e: Throwable?) {
        // Do Nothing
    }

    fun onLoginIdleEngine() {
        // Do Nothing
    }
}
