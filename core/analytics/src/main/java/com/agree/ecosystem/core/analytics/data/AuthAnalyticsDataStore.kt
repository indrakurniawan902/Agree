package com.agree.ecosystem.core.analytics.data

// import com.devbase.firebase.analytics.AnalyticsHandler
import androidx.core.os.bundleOf
import com.agree.ecosystem.core.analytics.AgrAnalytics
import com.agree.ecosystem.core.analytics.constant.FirebaseEventName
import com.agree.ecosystem.core.analytics.constant.FirebaseEventProperty
import com.agree.ecosystem.core.analytics.constant.FirebaseUserProperty
import com.agree.ecosystem.core.analytics.data.model.auth.LoginAnalyticsItem
import com.agree.ecosystem.core.analytics.data.model.auth.LogoutAnalyticsItem
import com.google.firebase.crashlytics.FirebaseCrashlytics

class AuthAnalyticsDataStore(
    private val analytics: AgrAnalytics
) : AuthAnalyticsRepository {
    override fun trackLogin(data: LoginAnalyticsItem) {
        analytics.setUserId(data.userId)
        analytics.setUserProperty(FirebaseUserProperty.USERNAME, data.username)
        analytics.logEvent(
            FirebaseEventName.ACTION_LOGIN,
            bundleOf(
                FirebaseEventProperty.USERNAME to data.username
            )
        )
        FirebaseCrashlytics.getInstance().apply {
            setUserId(data.userId)
            setCustomKey(FirebaseUserProperty.USERNAME, data.username)
            setCustomKey(FirebaseUserProperty.TOKEN, data.token)
            log("${data.username} has been login")
        }
    }

    override fun trackLogout(data: LogoutAnalyticsItem) {
        analytics.logEvent(
            FirebaseEventName.ACTION_LOGOUT,
            bundleOf(
                FirebaseEventProperty.USERNAME to data.username
            )
        )
    }
}
