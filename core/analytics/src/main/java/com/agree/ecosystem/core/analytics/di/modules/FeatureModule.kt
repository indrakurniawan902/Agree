package com.agree.ecosystem.core.analytics.di.modules

import com.agree.ecosystem.core.analytics.di.modules.auth.AuthModule
import org.koin.core.module.Module

interface FeatureModule :
    AuthModule {
    fun provideFeatureModule(): Array<Module> {
        return arrayOf(
            *provideAuthModule()
        )
    }
}
