package com.agree.ecosystem.core.utils.utility.extension

import android.app.Activity
import android.app.Service
import androidx.annotation.Keep
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.agree.ecosystem.core.utils.utility.AppConfig
import com.devbase.data.source.DevPreferenceManager
import com.google.firebase.crashlytics.FirebaseCrashlytics

@Keep
fun Fragment.setCrashKey(key: String, value: String) {
    FirebaseCrashlytics.getInstance().setCustomKey(key, value)
}

@Keep
fun ViewModel.setCrashKey(key: String, value: String) {
    FirebaseCrashlytics.getInstance().setCustomKey(key, value)
}

@Keep
fun Service.setCrashKey(key: String, value: String) {
    FirebaseCrashlytics.getInstance().setCustomKey(key, value)
}

@Keep
fun Activity.setCrashKey(key: String, value: String) {
    FirebaseCrashlytics.getInstance().setCustomKey(key, value)
}

@Keep
fun Fragment.logCrash(message: String) {
    FirebaseCrashlytics.getInstance().log(message)
}

@Keep
fun ViewModel.logCrash(message: String) {
    FirebaseCrashlytics.getInstance().log(message)
}

@Keep
fun Service.logCrash(message: String) {
    FirebaseCrashlytics.getInstance().log(message)
}

@Keep
fun Activity.logCrash(message: String) {
    FirebaseCrashlytics.getInstance().log(message)
}

@Keep
fun Fragment.recordException(exception: Throwable?) {
    exception?.let {
        if (!AppConfig.isDebug) {
            FirebaseCrashlytics.getInstance().recordException(exception)
        }
    }
}

@Keep
fun ViewModel.recordException(exception: Throwable?) {
    exception?.let {
        if (!AppConfig.isDebug) {
            FirebaseCrashlytics.getInstance().recordException(exception)
        }
    }
}

@Keep
fun Service.recordException(exception: Throwable?) {
    exception?.let {
        if (!AppConfig.isDebug) {
            FirebaseCrashlytics.getInstance().recordException(exception)
        }
    }
}

@Keep
fun Fragment.initCrashLog(username: String, userId: String, mitraId: String, token: String) {
    this.setCrashKey("username", username)
    this.setCrashKey("userId", userId)
    this.setCrashKey("mitraId", mitraId)
    this.setCrashKey("token", token)
    this.logCrash("User $username Login!")
    FirebaseCrashlytics.getInstance().setUserId(userId)
}

@Keep
fun Activity.initCrashLog(pref: DevPreferenceManager) {
    this.setCrashKey("username", pref.getString("username", ""))
    this.setCrashKey("userId", pref.getString("userId", ""))
    this.setCrashKey("mitraId", pref.getString("mitraId", ""))
    this.setCrashKey("token", pref.getString("user-token", ""))
    this.logCrash("User ${pref.getString("userId", "")} Login!")
    FirebaseCrashlytics.getInstance().setUserId(pref.getString("userId", ""))
}

@Keep
fun Fragment.initCrashLog(pref: DevPreferenceManager) {
    this.setCrashKey("username", pref.getString("username", ""))
    this.setCrashKey("userId", pref.getString("userId", ""))
    this.setCrashKey("mitraId", pref.getString("mitraId", ""))
    this.setCrashKey("token", pref.getString("user-token", ""))
    this.logCrash("User ${pref.getString("userId", "")} Login!")
    FirebaseCrashlytics.getInstance().setUserId(pref.getString("userId", ""))
}
