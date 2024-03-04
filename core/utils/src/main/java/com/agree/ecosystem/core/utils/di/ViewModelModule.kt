package com.agree.ecosystem.core.utils.di

import com.agree.ecosystem.core.utils.utility.validation.viewmodels.ValidationView
import com.agree.ecosystem.core.utils.utility.validation.viewmodels.ValidationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

interface ViewModelModule {
    fun provideViewModels(): Array<Module> {
        return arrayOf(
            provideValidation()
        )
    }

    fun provideValidation(): Module {
        return module {
            viewModel { (view: ValidationView) ->
                ValidationViewModel(view)
            }
        }
    }
}
