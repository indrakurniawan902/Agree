package com.agree.ecosystem.auth.di.modules.auth

import com.agree.ecosystem.auth.presentation.base.activity.AuthViewModel
import com.agree.ecosystem.auth.presentation.menu.changepassword.ChangePasswordViewModel
import com.agree.ecosystem.auth.presentation.menu.forgotaccount.ForgotAccountViewModel
import com.agree.ecosystem.auth.presentation.menu.login.LoginViewModel
import com.agree.ecosystem.auth.presentation.menu.register.RegisterViewModel
import com.agree.ecosystem.auth.presentation.menu.verification.OtpViewModel
import com.agree.ecosystem.auth.presentation.menu.verification.VerificationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

interface ViewModelModule {
    fun provideAuthViewModel(): Array<Module> {
        return arrayOf(
            provideLogin(),
            provideRegister(),
            provideForgotAccount(),
            provideVerification(),
            provideOtp(),
            provideChangePassword(),
            provideAuth()
        )
    }

    fun provideLogin(): Module {
        return module {
            viewModel {
                LoginViewModel(get(), get())
            }
        }
    }

    fun provideRegister(): Module {
        return module {
            viewModel {
                RegisterViewModel(get())
            }
        }
    }

    fun provideForgotAccount(): Module {
        return module {
            viewModel {
                ForgotAccountViewModel(get())
            }
        }
    }

    fun provideVerification(): Module {
        return module {
            viewModel {
                VerificationViewModel(get(), get())
            }
        }
    }

    fun provideOtp(): Module {
        return module {
            viewModel {
                OtpViewModel(get())
            }
        }
    }

    fun provideChangePassword(): Module {
        return module {
            viewModel {
                ChangePasswordViewModel(get())
            }
        }
    }

    fun provideAuth(): Module {
        return module {
            viewModel {
                AuthViewModel(get(), get())
            }
        }
    }
}
