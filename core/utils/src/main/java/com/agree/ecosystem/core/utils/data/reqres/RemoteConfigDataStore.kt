package com.agree.ecosystem.core.utils.data.reqres

import androidx.annotation.WorkerThread
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig

@WorkerThread
class RemoteConfigDataStore : RemoteConfigRepository {

    override fun getBoolean(key: String): Boolean {
        return Firebase.remoteConfig.getBoolean(key)
    }

    override fun getString(key: String): String {
        return Firebase.remoteConfig.getString(key)
    }

    override fun getDouble(key: String): Double {
        return Firebase.remoteConfig.getDouble(key)
    }

    override fun getLong(key: String): Long {
        return Firebase.remoteConfig.getLong(key)
    }
}
