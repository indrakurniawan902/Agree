package com.agree.ecosystem.smartfarming.di.modules.smartfarming

import com.agree.ecosystem.smartfarming.presentation.menu.monitoring.MonitoringViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

interface ViewModelModule {
    fun provideSmartFarmingViewModel(): Array<Module> {
        return arrayOf(
            provideParameterTest()
        )
    }

    fun provideParameterTest() : Module {
        return module {
            viewModel {
                MonitoringViewModel(get())
            }
        }
    }
}
