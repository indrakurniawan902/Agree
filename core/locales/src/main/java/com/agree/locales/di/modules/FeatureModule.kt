package com.agree.locales.di.modules

import com.agree.locales.di.modules.locale.LocaleModule
import org.koin.core.module.Module

interface FeatureModule : LocaleModule{
    fun provideFeatureModule(): Array<Module> {
        return arrayOf(
            *provideLocaleModule()
        )
    }
}