package com.agree.ecosystem.finances.di.modules.finance

import com.agree.ecosystem.finances.presentation.menu.finance.cultivator.data.listcultivator.ListCultivatorViewModel
import com.agree.ecosystem.finances.presentation.menu.finance.cultivator.data.profilefarmer.ProfileCultivatorViewModel
import com.agree.ecosystem.finances.presentation.menu.finance.cultivator.data.profilefarmer.dynamic.DynamicFormInfoCultivatorViewModel
import com.agree.ecosystem.finances.presentation.menu.finance.loanactive.ListLoanActiveViewModel
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.LoanSubmissionViewModel
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.detail.DetailLoanPackageViewModel
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.listloan.ListLoanPackageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * ViewModel Module are modules that are responsible for injection of classes
 * related to ViewModels on Finance Module
 * @author: @telkomdev-abdul
 * @since: 10 Jan 2023
 */
interface ViewModelModule {
    fun provideFinanceViewModel(): Array<Module> {
        return arrayOf(
            provideFinanceLoanPackageViewModel(),
            provideFinanceDataCultivatorViewModel(),
            provideFinanceLoanActiveViewModel(),
            provideDetailLoanPackage(),
            provideProfileCultivatorViewModel(),
            provideLoanSubmissionViewModel(),
            provideDynamicFormViewModel()
        )
    }

    fun provideDynamicFormViewModel(): Module {
        return module {
            viewModel {
                DynamicFormInfoCultivatorViewModel(get())
            }
        }
    }

    fun provideFinanceLoanPackageViewModel(): Module {
        return module {
            viewModel {
                ListLoanPackageViewModel(get())
            }
        }
    }

    fun provideFinanceDataCultivatorViewModel(): Module {
        return module {
            viewModel {
                ListCultivatorViewModel(get())
            }
        }
    }

    fun provideFinanceLoanActiveViewModel(): Module {
        return module {
            viewModel {
                ListLoanActiveViewModel(get())
            }
        }
    }

    fun provideDetailLoanPackage(): Module {
        return module {
            viewModel {
                DetailLoanPackageViewModel(get())
            }
        }
    }

    fun provideProfileCultivatorViewModel(): Module {
        return module {
            viewModel {
                ProfileCultivatorViewModel(get())
            }
        }
    }

    fun provideLoanSubmissionViewModel(): Module {
        return module {
            viewModel {
                LoanSubmissionViewModel(get())
            }
        }
    }
}
