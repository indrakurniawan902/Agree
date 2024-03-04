package com.agree.ecosystem.splash.di.modules.splash

import com.agree.ecosystem.splash.presentation.menu.updater.UpdaterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

interface ViewModelModule {
    fun provideSplashViewModel(): Array<Module> {
        return arrayOf(
            provideAppInfo()
        )
    }

    fun provideAppInfo(): Module {
        return module {
            viewModel {
                UpdaterViewModel(get(), get())
            }
        }
    }
}
