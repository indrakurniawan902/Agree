package com.agree.ecosystem.common.di.modules.finance

import com.agree.ecosystem.common.presentation.menu.finance.FinanceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

interface ViewModelModule {
    fun provideFinanceViewModel(): Array<Module> {
        return arrayOf(provideLoanActive())
    }

    fun provideLoanActive(): Module {
        return module {
            viewModel {
                FinanceViewModel(get())
            }
        }
    }
}
