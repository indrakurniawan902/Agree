package com.agree.ecosystem.core.analytics.domain

import com.agree.ecosystem.core.analytics.data.AuthAnalyticsRepository
import com.agree.ecosystem.core.analytics.data.model.auth.LoginAnalyticsItem
import com.agree.ecosystem.core.analytics.data.model.auth.LogoutAnalyticsItem

class AuthAnalyticsInteractor(
    private val repo: AuthAnalyticsRepository
) : AuthAnalyticsUsecase {
    override fun trackLogin(data: LoginAnalyticsItem) {
        repo.trackLogin(data)
    }

    override fun trackLogout(data: LogoutAnalyticsItem) {
        repo.trackLogout(data)
    }
}
