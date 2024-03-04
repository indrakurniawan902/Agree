package com.agree.ecosystem.core.analytics.di.modules.auth

import com.agree.ecosystem.core.analytics.data.AuthAnalyticsDataStore
import com.agree.ecosystem.core.analytics.data.AuthAnalyticsRepository
import com.agree.ecosystem.core.analytics.domain.AuthAnalyticsInteractor
import com.agree.ecosystem.core.analytics.domain.AuthAnalyticsUsecase
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Auth Module are modules that are responsible for injection of classes
 * related to Services, Repository and UseCase
 * @author: @telkomdev-abdul
 * @since: 04 Oct 2022
 */
interface AuthModule {
    fun provideAuthModule(): Array<Module> {
        return arrayOf(
            provideAuthRepository(),
            provideAuthUseCase()
        )
    }

    fun provideAuthRepository(): Module {
        return module {
            single {
                AuthAnalyticsDataStore(get())
            } bind AuthAnalyticsRepository::class
        }
    }

    fun provideAuthUseCase(): Module {
        return module {
            single {
                AuthAnalyticsInteractor(get())
            } bind AuthAnalyticsUsecase::class
        }
    }
}
