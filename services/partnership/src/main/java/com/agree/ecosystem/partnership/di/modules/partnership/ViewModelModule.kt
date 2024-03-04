package com.agree.ecosystem.partnership.di.modules.partnership

import com.agree.ecosystem.partnership.presentation.menu.companies.CompaniesViewModel
import com.agree.ecosystem.partnership.presentation.menu.companies.detail.DetailCompanyViewModel
import com.agree.ecosystem.partnership.presentation.menu.registration.RegistrationViewModel
import com.agree.ecosystem.partnership.presentation.menu.statusregistration.StatusRegistrationViewModel
import com.agree.ecosystem.partnership.presentation.menu.statusregistration.detail.DetailStatusRegistrationViewModel
import com.agree.ecosystem.utilities.presentation.menu.sectorsdialog.SectorsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * ViewModel Module are modules that are responsible for injection of classes
 * related to ViewModels on Partnership Module
 * @author: @telkomdev-abdul
 * @since: 04 Oct 2022
 */
interface ViewModelModule {
    fun providePartnershipViewModel(): Array<Module> {
        return arrayOf(
            provideCompanies(),
            provideRegistration(),
            provideDetailStatus(),
            provideStatusRegister(),
            provideDetailCompany(),
            provideSector()
        )
    }

    fun provideStatusRegister(): Module {
        return module {
            viewModel {
                StatusRegistrationViewModel(get())
            }
        }
    }

    fun provideCompanies(): Module {
        return module {
            viewModel {
                CompaniesViewModel(get())
            }
        }
    }

    fun provideDetailCompany(): Module {
        return module {
            viewModel {
                DetailCompanyViewModel(get())
            }
        }
    }

    fun provideDetailStatus(): Module {
        return module {
            viewModel {
                DetailStatusRegistrationViewModel(get())
            }
        }
    }

    fun provideRegistration(): Module {
        return module {
            viewModel {
                RegistrationViewModel(get())
            }
        }
    }

    fun provideSector(): Module {
        return module {
            viewModel {
                SectorsViewModel(get())
            }
        }
    }
}
