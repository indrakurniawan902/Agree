package com.agree.ecosystem.core.utils.data.reqres.sharedprefs

import android.content.Context
import com.agree.ecosystem.core.utils.utility.AppConfig
import com.devbase.data.source.DevPreferenceManager
import com.google.gson.Gson

class AgrPrefsManager(
    private val context: Context,
    private val prefName: String,
    private val gson: Gson
) {
    private val mPreferences by lazy {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    private val encryptedPreferences by lazy {
        DevPreferenceManager(
            context,
            prefName,
            Gson()
        )
    }

    /**
     * Checking if application is debug or not
     */
    private val isNeedEncrypt: Boolean by lazy { !AppConfig.isDebug }

    /**
     * Method to get boolean from preference
     * @param key preference's name
     * @param default preference's value when the value is null
     * @return boolean value of desired preference
     */
    fun getBoolean(key: String, default: Boolean): Boolean {
        return if (isNeedEncrypt) {
            encryptedPreferences.getBoolean(key, default)
        } else {
            mPreferences.getBoolean(key, default)
        }
    }

    /**
     * Method to save boolean to preference
     * @param key preference's name
     * @param value preference's value
     */
    fun saveBoolean(key: String, value: Boolean) {
        if (isNeedEncrypt) {
            encryptedPreferences.saveBoolean(key, value)
        } else {
            mPreferences.edit().putBoolean(key, value).apply()
        }
    }

    /**
     * Method to get int from preference
     * @param key preference's name
     * @param default preference's value when the value is null
     * @return int value of desired preference
     */
    fun getInt(key: String, default: Int): Int {
        return if (isNeedEncrypt) {
            encryptedPreferences.getInt(key, default)
        } else {
            mPreferences.getInt(key, default)
        }
    }

    /**
     * Method to save int to preference
     * @param key preference's name
     * @param value preference's value
     */
    fun saveInt(key: String, value: Int) {
        if (isNeedEncrypt) {
            encryptedPreferences.saveInt(key, value)
        } else {
            mPreferences.edit().putInt(key, value).apply()
        }
    }

    /**
     * Method to get string from preference
     * @param key preference's name
     * @param default preference's default value when the value is null
     * @return string value of desired preference
     */
    fun getString(key: String, default: String): String {
        return if (isNeedEncrypt) {
            encryptedPreferences.getString(key, default)
        } else {
            mPreferences.getString(key, default).orEmpty()
        }
    }

    /**
     * Method to save string to preference
     * @param key preference's name
     * @param value preference's value
     */
    fun saveString(key: String, value: String) {
        if (isNeedEncrypt) {
            encryptedPreferences.saveString(key, value)
        } else {
            mPreferences.edit().putString(key, value).apply()
        }
    }

    /**
     * Method to get long from preference
     * @param key preference's name
     * @param default preference's value when the value is null
     * @return long value of desired preference
     */
    fun getLong(key: String, default: Long): Long {
        return if (isNeedEncrypt) {
            encryptedPreferences.getLong(key, default)
        } else {
            mPreferences.getLong(key, default)
        }
    }

    /**
     * Method to save long to preference
     * @param key preference's name
     * @param value preference's value
     */
    fun saveLong(key: String, value: Long) {
        if (isNeedEncrypt) {
            encryptedPreferences.saveLong(key, value)
        } else {
            mPreferences.edit().putLong(key, value).apply()
        }
    }

    /**
     * Method to get float from preference
     * @param key preference's name
     * @param default preference's value when the value is null
     * @return float value of desired preference
     */
    fun getFloat(key: String, default: Float): Float {
        return if (isNeedEncrypt) {
            encryptedPreferences.getFloat(key, default)
        } else {
            mPreferences.getFloat(key, default)
        }
    }

    /**
     * Method to save float to preference
     * @param key preference's name
     * @param value preference's value
     */
    fun saveFloat(key: String, value: Float) {
        if (isNeedEncrypt) {
            encryptedPreferences.saveFloat(key, value)
        } else {
            mPreferences.edit().putFloat(key, value).apply()
        }
    }

    /**
     * Method to get object from preference
     * @param key preference's name
     * @return object value of desired preference
     */
    fun <T> getObject(key: String, type: Class<T>): T? {
        val json = getString(key, "")

        return if (json.isNotEmpty())
            try {
                gson.fromJson(json, type)
            } catch (exception: Exception) {
                null
            }
        else
            null
    }

    /**
     * Method to save object to preference
     * @param key preference's name
     * @param value preference's value
     */
    fun saveObject(key: String, value: Any) {
        val json = gson.toJson(value)
        mPreferences.edit().putString(key, json).apply()
    }
}
