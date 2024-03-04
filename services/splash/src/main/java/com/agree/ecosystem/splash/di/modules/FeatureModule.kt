package com.agree.ecosystem.splash.di.modules

import com.agree.ecosystem.splash.di.modules.splash.SplashModule
import org.koin.core.module.Module

interface FeatureModule :
    SplashModule {
    fun provideFeatureModule(): Array<Module> {
        return arrayOf(
            *provideSplashModule()
        )
    }
}
