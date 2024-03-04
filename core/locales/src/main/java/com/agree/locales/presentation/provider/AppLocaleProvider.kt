package com.agree.locales.presentation.provider

import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import dev.b3nedikt.app_locale.AppLocale
import dev.b3nedikt.restring.LocaleProvider
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

/**
 * App Locale Provider for Locale Framework, where locale configuration saved
 */
object AppLocaleProvider : LocaleProvider, KoinComponent {

    private val prefs: AgrPrefUsecase by inject()

    override var currentLocale: Locale
        get() = Locale.forLanguageTag(prefs.currentLocale)
        set(value) {
            AppLocale.desiredLocale = value
        }

    override val isInitial: Boolean
        get() = AppLocale.isInitial

}