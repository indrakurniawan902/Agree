package com.agree.ecosystem.users.di.modules.profile

import com.agree.ecosystem.users.presentation.menu.profile.ProfileViewModel
import com.agree.ecosystem.users.presentation.menu.updateprofile.UpdateProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * ViewModel Module are modules that are responsible for injection of classes
 * related to ViewModels on Profile Module
 * @author: @telkomdev-abdul
 * @since: 20 Dec 2022
 */
interface ViewModelModule {
    fun provideProfileViewModel(): Array<Module> {
        return arrayOf(
            provideProfile(),
            provideUpdateProfile()
        )
    }

    fun provideProfile(): Module {
        return module {
            viewModel {
                ProfileViewModel(get())
            }
        }
    }

    fun provideUpdateProfile(): Module {
        return module {
            viewModel {
                UpdateProfileViewModel(get())
            }
        }
    }
}
