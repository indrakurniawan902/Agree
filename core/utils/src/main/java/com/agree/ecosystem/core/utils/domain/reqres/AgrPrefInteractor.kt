package com.agree.ecosystem.core.utils.domain.reqres

import com.agree.ecosystem.core.utils.data.reqres.AgrPrefRepository
import com.agree.ecosystem.core.utils.data.reqres.sharedprefs.AgrPrefsManager

class AgrPrefInteractor(
    private val repo: AgrPrefRepository
) : AgrPrefUsecase {
    override val get: AgrPrefsManager
        get() = repo.get

    override var accessToken: String
        get() = repo.accessToken
        set(value) {
            repo.accessToken = value
        }

    override var accessTokenEngine: String
        get() = repo.accessTokenEngine
        set(value) {
            repo.accessTokenEngine = value
        }

    override var refreshToken: String
        get() = repo.refreshToken
        set(value) {
            repo.refreshToken = value
        }

    override var phoneNumber: String
        get() = repo.phoneNumber
        set(value) {
            repo.phoneNumber = value
        }

    override var userId: String
        get() = repo.userId
        set(value) {
            repo.userId = value
        }

    override var userName: String
        get() = repo.userName
        set(value) {
            repo.userName = value
        }

    override var name: String
        get() = repo.name
        set(value) {
            repo.name = value
        }

    override var rememberUsername: String
        get() = repo.rememberUsername
        set(value) {
            repo.rememberUsername = value
        }

    override var contactInfo: String
        get() = repo.contactInfo
        set(value) {
            repo.contactInfo = value
        }

    override var fcmToken: String
        get() = repo.fcmToken
        set(value) {
            repo.fcmToken = value
        }

    override var lastLanguageFetch: Long
        get() = repo.lastLanguageFetch
        set(value) {
            repo.lastLanguageFetch = value
        }
    override var currentLocale: String
        get() = repo.currentLocale
        set(value) {
            repo.currentLocale = value
        }

    override var shouldShowSmartFarmingCoachmark: Boolean
        get() = repo.shouldShowSmartFarmingCoachmark
        set(value) {
            repo.shouldShowSmartFarmingCoachmark = value
        }

    override var isRemember: Boolean
        get() = repo.isRemember
        set(value) {
            repo.isRemember = value
        }

    override fun clear() {
        repo.clear()
    }
}
