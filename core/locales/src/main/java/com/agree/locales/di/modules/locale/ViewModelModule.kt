package com.agree.locales.di.modules.locale

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * ViewModel Module are modules that are responsible for injection of classes
 * related to ViewModels on Locale Module
 * @author: @telkomdev-abdul
 * @since: 21 Dec 2022
 */
interface ViewModelModule {
    fun provideLocaleViewModel(): Array<Module> {
        return arrayOf()
    }
}