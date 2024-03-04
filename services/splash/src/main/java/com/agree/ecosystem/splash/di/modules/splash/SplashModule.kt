package com.agree.ecosystem.splash.di.modules.splash

import org.koin.core.module.Module

interface SplashModule : ViewModelModule {
    fun provideSplashModule(): Array<Module> {
        return arrayOf(
            *provideSplashViewModel()
        )
    }
}
