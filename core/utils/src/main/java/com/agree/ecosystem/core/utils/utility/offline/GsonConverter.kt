package com.agree.ecosystem.core.utils.utility.offline

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface GsonConverter {
    /**
     * Convert Any Class to Json using Gson
     */
    fun <T> toJson(data: T?): String {
        if (data == null) return ""

        val typeToken = object : TypeToken<T>() {}.type
        return Gson().toJson(data, typeToken)
    }

    /**
     * Convert Json to Any Class using Gson
     */
    fun <T> toObject(data: String?): T? {
        if (data.isNullOrEmpty()) return null

        val typeToken = object : TypeToken<T>() {}.type
        return Gson().fromJson(data, typeToken)
    }
}
