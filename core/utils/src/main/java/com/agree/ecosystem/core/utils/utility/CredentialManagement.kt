package com.agree.ecosystem.core.utils.utility

object CredentialManagement {
    init {
        System.loadLibrary("credentials")
    }

    external fun getUsernameEngine(): String
    external fun getPasswordEngine(): String
    external fun getAgreepediaToken(): String
    external fun getBasicToken(): String
    external fun getUsernameLocale(): String
    external fun getPasswordLocale(): String
    external fun getLocaleToken(): String
}
