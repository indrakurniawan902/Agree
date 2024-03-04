package com.agree.ecosystem.core.utils.data.reqres

import com.agree.ecosystem.core.utils.data.reqres.sharedprefs.AgrPrefsManager
import com.agree.ecosystem.core.utils.utility.Constant
import java.util.Locale

class AgrPrefDataStore(
    private val prefs: AgrPrefsManager
) : AgrPrefRepository {
    override val get: AgrPrefsManager
        get() = prefs

    override var accessToken: String
        get() = prefs.getString(Constant.PREF_ACCESS_TOKEN, "")
        set(value) {
            prefs.saveString(Constant.PREF_ACCESS_TOKEN, value)
        }

    override var accessTokenEngine: String
        get() = prefs.getString(Constant.PREF_ACCESS_TOKEN_MONITORING, "")
        set(value) {
            prefs.saveString(Constant.PREF_ACCESS_TOKEN_MONITORING, value)
        }

    override var refreshToken: String
        get() = prefs.getString(Constant.PREF_REFRESH_TOKEN, "")
        set(value) {
            prefs.saveString(Constant.PREF_REFRESH_TOKEN, value)
        }

    override var phoneNumber: String
        get() = prefs.getString(Constant.PREF_PHONE_NUMBER, "")
        set(value) {
            prefs.saveString(Constant.PREF_PHONE_NUMBER, value)
        }

    override var userId: String
        get() = prefs.getString(Constant.PREF_USER_ID, "")
        set(value) {
            prefs.saveString(Constant.PREF_USER_ID, value)
        }

    override var userName: String
        get() = prefs.getString(Constant.PREF_USERNAME, "")
        set(value) {
            prefs.saveString(Constant.PREF_USERNAME, value)
        }

    override var name: String
        get() = prefs.getString(Constant.PREF_FULL_NAME, "")
        set(value) {
            prefs.saveString(Constant.PREF_FULL_NAME, value)
        }

    override var rememberUsername: String
        get() = prefs.getString(Constant.PREF_REMEMBER_USERNAME, String())
        set(value) {
            prefs.saveString(Constant.PREF_REMEMBER_USERNAME, value)
        }

    override var contactInfo: String
        get() = prefs.getString(Constant.PREF_CONTACT_INFO, "")
        set(value) {
            prefs.saveString(Constant.PREF_CONTACT_INFO, value)
        }

    override var fcmToken: String
        get() = prefs.getString(Constant.PREF_FCM_TOKEN, "")
        set(value) {
            prefs.saveString(Constant.PREF_FCM_TOKEN, value)
        }

    override var lastLanguageFetch: Long
        get() = prefs.getLong(Constant.PREFS_LAST_FETCH_LOCALE, 0L)
        set(value) {
            prefs.saveLong(Constant.PREFS_LAST_FETCH_LOCALE, value)
        }

    override var currentLocale: String
        get() = prefs.getString(
            Constant.PREF_CURRENT_LOCALE,
            Locale("id", "ID")
                .toLanguageTag()
        )
        set(value) {
            prefs.saveString(Constant.PREF_CURRENT_LOCALE, value)
        }
    override var shouldShowSmartFarmingCoachmark: Boolean
        get() = prefs.getBoolean(
            Constant.PREFS_SHOULD_SHOW_SMART_FARMING_COACHMARK,
            true
        )
        set(value) {
            prefs.saveBoolean(Constant.PREFS_SHOULD_SHOW_SMART_FARMING_COACHMARK, value)
        }

    override var isRemember: Boolean
        get() = prefs.getBoolean(Constant.PREF_IS_REMEMBER, false)
        set(value) {
            prefs.saveBoolean(Constant.PREF_IS_REMEMBER, value)
        }

    override fun clear() {
        accessToken = ""
        refreshToken = ""
        userId = ""
        if (!isRemember) userName = ""
        name = ""
        shouldShowSmartFarmingCoachmark = true
    }
}
