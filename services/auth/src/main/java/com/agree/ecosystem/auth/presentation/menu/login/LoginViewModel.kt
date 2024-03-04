package com.agree.ecosystem.auth.presentation.menu.login

import com.agree.ecosystem.auth.domain.reqres.AuthUsecase
import com.agree.ecosystem.auth.domain.reqres.model.login.Engine
import com.agree.ecosystem.auth.domain.reqres.model.login.Login
import com.agree.ecosystem.core.analytics.data.model.auth.LoginAnalyticsItem
import com.agree.ecosystem.core.analytics.domain.AuthAnalyticsUsecase
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class LoginViewModel(
    private val usecase: AuthUsecase,
    private val authAnalytics: AuthAnalyticsUsecase
) : DevViewModel() {
    private val _login = DevData<Pair<Login, Engine>>().apply { default() }
    val login = _login.immutable()

    fun doLogin(
        userName: String,
        password: String,
        block: (Int.(String?) -> Unit) = {}
    ) {
        usecase.login(userName, password)
            .setHandler(_login, block)
            .let { return@let disposable::add }
    }

    fun trackLogin(
        data: LoginAnalyticsItem
    ) {
        authAnalytics.trackLogin(data)
    }
}
