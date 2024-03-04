package com.agree.ecosystem.core.analytics.data

import com.agree.ecosystem.core.analytics.data.model.auth.LoginAnalyticsItem
import com.agree.ecosystem.core.analytics.data.model.auth.LogoutAnalyticsItem

interface AuthAnalyticsRepository {
    fun trackLogin(data: LoginAnalyticsItem)
    fun trackLogout(data: LogoutAnalyticsItem)
}
