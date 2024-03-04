package com.agree.ecosystem.utilities.di.modules.utilities

import com.agree.ecosystem.utilities.presentation.menu.about.AboutAgreeViewModel
import com.agree.ecosystem.utilities.presentation.menu.help.HelpViewModel
import com.agree.ecosystem.utilities.presentation.menu.termsconditions.TermsConditionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * ViewModel Module are modules that are responsible for injection of classes
 * related to ViewModels on Utilities Module
 * @author: @telkomdev-abdul
 * @since: 23 Nov 2022
 */
interface ViewModelModule {
    fun provideUtilitiesViewModel(): Array<Module> {
        return arrayOf(
            provideAboutAgree(),
            provideHelp(),
            provideTermsConditions()
        )
    }

    fun provideAboutAgree(): Module {
        return module {
            viewModel {
                AboutAgreeViewModel(get())
            }
        }
    }

    fun provideTermsConditions(): Module {
        return module {
            viewModel {
                TermsConditionsViewModel(get())
            }
        }
    }

    fun provideHelp(): Module {
        return module {
            viewModel {
                HelpViewModel(get())
            }
        }
    }
}
