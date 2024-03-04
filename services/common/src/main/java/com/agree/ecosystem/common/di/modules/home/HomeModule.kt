package com.agree.ecosystem.common.di.modules.home

import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Home Module are modules that are responsible for injection of classes
 * related to Services, Repository and UseCase
 * @author: @telkomdev-abdul
 * @since: 04 Oct 2022
 */
interface HomeModule : ViewModelModule {
    fun provideHomeModule(): Array<Module> {
        return arrayOf(
            provideHomeService(),
            provideHomeRepository(),
            provideHomeUseCase(),
            *provideHomeViewModel()
        )
    }

    fun provideHomeService(): Module {
        return module {
        }
    }

    fun provideHomeRepository(): Module {
        return module {
        }
    }

    fun provideHomeUseCase(): Module {
        return module {
        }
    }
}
