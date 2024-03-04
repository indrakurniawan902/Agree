package com.agree.ecosystem.core.utils.utility.gson

import com.devbase.data.source.DevRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Convert Any Class to Json using Gson
 */
fun <T> DevRepository.toJson(data: T): String {
    val typeToken = object : TypeToken<T>() {}.type
    return Gson().toJson(data, typeToken)
}

/**
 * Convert Json to Any Class using Gson
 */
fun <T> DevRepository.toObject(data: String): T {
    val typeToken = object : TypeToken<T>() {}.type
    return Gson().fromJson(data, typeToken)
}
