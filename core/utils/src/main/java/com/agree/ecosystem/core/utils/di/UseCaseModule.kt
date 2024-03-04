package com.agree.ecosystem.core.utils.di

import com.agree.ecosystem.core.utils.domain.reqres.CommonInteractor
import com.agree.ecosystem.core.utils.domain.reqres.CommonUsecase
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Repository Injection Module are modules that are responsible for injection of classes
 * related to UseCase and Interactor
 * @author: @telkomdev-abdul
 * @since: 3 Oct 2022
 */
interface UseCaseModule {
    fun provideUseCases(): Array<Module> {
        return arrayOf(
            provideCommonUseCase()
        )
    }

    fun provideCommonUseCase(): Module {
        return module {
            single { CommonInteractor(get()) } bind CommonUsecase::class
        }
    }
}
