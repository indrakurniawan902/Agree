package com.agree.ecosystem.core.utils.data.reqres

import com.agree.ecosystem.core.utils.data.reqres.sharedprefs.AgrPrefsManager

interface AgrPrefRepository {
    val get: AgrPrefsManager

    var accessToken: String
    var refreshToken: String
    var accessTokenEngine: String
    var phoneNumber: String
    var userId: String
    var userName: String
    var name: String
    var rememberUsername: String
    var contactInfo: String
    var fcmToken: String
    var isRemember: Boolean

    var lastLanguageFetch: Long
    var currentLocale: String

    var shouldShowSmartFarmingCoachmark: Boolean

    fun clear()
}
