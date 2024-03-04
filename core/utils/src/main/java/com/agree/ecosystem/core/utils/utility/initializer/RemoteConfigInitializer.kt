package com.agree.ecosystem.core.utils.utility.initializer

import android.content.Context
import androidx.startup.Initializer
import com.agree.ecosystem.core.utils.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class RemoteConfigInitializer : Initializer<Unit> {

    companion object {
        /**
         * Interval untuk melakukan update remote config (dalam detik)
         */
        const val FETCH_INTERVAL_DEV = 3600L
        const val FETCH_INTERVAL = 1L

        /**
         * Timeout untuk fetching remote config (dalam detik)
         */
        const val FETCH_TIMEOUT = 2L
    }

    override fun create(context: Context) {
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds =
                FETCH_INTERVAL
            fetchTimeoutInSeconds = FETCH_TIMEOUT
        }
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.setConfigSettingsAsync(configSettings)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(FirebaseInitializer::class.java)
    }
}
