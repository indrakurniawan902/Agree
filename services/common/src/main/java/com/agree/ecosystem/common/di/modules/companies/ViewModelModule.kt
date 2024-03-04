package com.agree.ecosystem.common.di.modules.companies

import com.agree.ecosystem.common.presentation.menu.partnership.PartnershipViewModel
import com.agree.ecosystem.common.presentation.menu.partnership.detail.DetailPartnershipViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * ViewModel Module are modules that are responsible for injection of classes
 * related to ViewModels on Companies Module
 * @author: @telkomdev-abdul
 * @since: 04 Oct 2022
 */
interface ViewModelModule {
    fun provideCompaniesViewModel(): Array<Module> {
        return arrayOf(
            providePartnership(),
            provideDetailPartnership()
        )
    }

    fun providePartnership(): Module {
        return module {
            viewModel {
                PartnershipViewModel(get())
            }
        }
    }

    fun provideDetailPartnership(): Module {
        return module {
            viewModel {
                DetailPartnershipViewModel(get())
            }
        }
    }
}
