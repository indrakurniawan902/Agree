package com.agree.ecosystem.core.utils.data.reqres

interface RemoteConfigRepository {
    fun getBoolean(key: String): Boolean
    fun getString(key: String): String
    fun getDouble(key: String): Double
    fun getLong(key: String): Long
}
