package com.agree.ecosystem.common.di.modules.utilities

import com.agree.ecosystem.common.presentation.menu.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * ViewModel Module are modules that are responsible for injection of classes
 * related to ViewModels on Utilities Module
 * @author: @telkomdev-abdul
 * @since: 04 Oct 2022
 */
interface ViewModelModule {
    fun provideUtilitiesViewModel(): Array<Module> {
        return arrayOf(
            provideSettings(),
//            provideMain()
        )
    }

    fun provideSettings(): Module {
        return module {
            viewModel {
                SettingsViewModel(get())
            }
        }
    }
}
