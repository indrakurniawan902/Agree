package com.agree.ecosystem.common.di.modules.home

import com.agree.ecosystem.common.presentation.menu.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * ViewModel Module are modules that are responsible for injection of classes
 * related to ViewModels on Home Module
 * @author: @telkomdev-abdul
 * @since: 04 Oct 2022
 */
interface ViewModelModule {
    fun provideHomeViewModel(): Array<Module> {
        return arrayOf(
            provideHome()
        )
    }

    fun provideHome(): Module {
        return module {
            viewModel {
                HomeViewModel(get(), get(), get(), get(), get(), get())
            }
        }
    }
}
