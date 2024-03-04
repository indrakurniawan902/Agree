package com.agree.ecosystem.core.utils.utility

object UrlManagement {
    init {
        System.loadLibrary("native-lib")
    }

    /**
     * Base Url Agree Mitra & Crud Engine
     */
    private external fun getBaseUrlDev(): String
    private external fun getBaseUrlStaging(): String
    private external fun getBaseUrlProd(): String

    /**
     * Base Url Agreepedia
     */
    private external fun getAgreepediaBaseUrlDev(): String
    private external fun getAgreepediaBaseUrlStaging(): String
    private external fun getAgreepediaBaseUrlProd(): String

    external fun getLocaleBaseUrl(): String

    fun getBaseUrl(): String {
        return when (AppConfig.env) {
            "prod" -> getBaseUrlProd()
            "staging" -> getBaseUrlStaging()
            else -> getBaseUrlDev()
        }
    }

    fun getAgreepediaBaseUrl(): String {
        return when (AppConfig.env) {
            "prod" -> getAgreepediaBaseUrlProd()
            "staging" -> getAgreepediaBaseUrlStaging()
            else -> getAgreepediaBaseUrlDev()
        }
    }
}
