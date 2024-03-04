package com.agree.ecosystem.auth.presentation.base.activity

import com.agree.ecosystem.auth.domain.reqres.AuthUsecase
import com.agree.ecosystem.core.analytics.data.model.auth.LogoutAnalyticsItem
import com.agree.ecosystem.core.analytics.domain.AuthAnalyticsUsecase
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel
import com.google.gson.JsonElement

class AuthViewModel(
    private val authAnalytics: AuthAnalyticsUsecase,
    private val usecase: AuthUsecase
) : DevViewModel() {
    private val _logout = DevData<JsonElement>().apply { default() }
    val logout = _logout.immutable()

    fun logout() {
        usecase.logout()
            .setHandler(_logout)
            .let { return@let disposable::add }
    }

    fun trackLogout(username: String) {
        authAnalytics.trackLogout(LogoutAnalyticsItem(username))
    }
}
