package com.agree.ecosystem.core.utils.di

import com.agree.ecosystem.core.utils.data.reqres.*
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Repository Injection Module are modules that are responsible for injection of classes
 * related to Repository and DataStore
 * @author: @telkomdev-abdul
 * @since: 3 Oct 2022
 */
interface RepositoryModule {

    /**
     * Group all module into array
     */
    fun provideRepositories(): Array<Module> {
        return arrayOf(
            provideCommonRepository(),
            provideEngineTokenRepository(),
            provideTokenRepository(),
            provideRemoteConfigRepository()
        )
    }

    fun provideCommonRepository(): Module {
        return module {
            single {
                CommonDataStore(null, get())
            } bind CommonRepository::class
        }
    }

    fun provideEngineTokenRepository(): Module {
        return module {
            single {
                EngineTokenDataStore(null, get())
            } bind EngineTokenRepository::class
        }
    }

    fun provideTokenRepository(): Module {
        return module {
            single {
                TokenDataStore(null, get())
            } bind TokenRepository::class
        }
    }

    fun provideRemoteConfigRepository(): Module {
        return module {
            single {
                RemoteConfigDataStore()
            } bind RemoteConfigRepository::class
        }
    }
}
