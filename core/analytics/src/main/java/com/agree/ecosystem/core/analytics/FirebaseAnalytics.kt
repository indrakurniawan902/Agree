package com.agree.ecosystem.core.analytics

import android.os.Bundle
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class FirebaseAnalytics : AgrAnalytics {
    private val analytics = Firebase.analytics

    override fun logEvent(eventName: String, params: Bundle?) {
        analytics.logEvent(eventName, params)
    }

    override fun setUserProperty(name: String, value: String) {
        analytics.setUserProperty(name, value)
    }

    override fun setUserId(id: String) {
        analytics.setUserId(id)
    }

    override fun clearData() {
        analytics.resetAnalyticsData()
    }

    override fun instanceId(listener: (String) -> Unit) {
        analytics.appInstanceId.addOnCompleteListener { if (it.isSuccessful) listener.invoke(it.result) }
    }
}
