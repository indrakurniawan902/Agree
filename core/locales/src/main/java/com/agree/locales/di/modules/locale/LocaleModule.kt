package com.agree.locales.di.modules.locale

import com.agree.ecosystem.core.utils.utility.Constant
import com.agree.ecosystem.core.utils.utility.UrlManagement
import com.agree.ecosystem.core.utils.utility.createRoomDb
import com.agree.locales.data.LocaleDatabase
import com.agree.locales.data.reqres.LocaleDataStore
import com.agree.locales.data.reqres.LocaleRepository
import com.agree.locales.data.reqres.db.LocaleDb
import com.agree.locales.data.reqres.db.LocaleDbImpl
import com.agree.locales.data.reqres.web.LocaleApiClient
import com.agree.locales.domain.LocaleInteractor
import com.agree.locales.domain.LocaleUsecase
import com.devbase.data.source.web.libs.createReactiveService
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Locale Module are modules that are responsible for injection of classes
 * related to Services, Repository and UseCase
 * @author: @telkomdev-abdul
 * @since: 21 Dec 2022
 */
interface LocaleModule : ViewModelModule {
    fun provideLocaleModule(): Array<Module> {
        return arrayOf(
            provideLocaleService(),
            provideLocaleDatabase(),
            provideLocaleRepository(),
            provideLocaleUseCase(),
            *provideLocaleViewModel()
        )
    }

    fun provideLocaleService(): Module {
        return module {
            single(named(Constant.LOCALE_SERVICES)) {
                createReactiveService(
                    LocaleApiClient::class.java,
                    get { parametersOf(false, true) },
                    UrlManagement.getLocaleBaseUrl()
                )
            }
        }
    }

    fun provideLocaleDatabase(): Module {
        return module {
            single { createRoomDb<LocaleDatabase>(androidContext(), LocaleDatabase.getDbName()) }
            single { LocaleDbImpl(get()) } bind LocaleDb::class
        }
    }

    fun provideLocaleRepository(): Module {
        return module {
            single {
                LocaleDataStore(
                    androidContext(),
                    get(),
                    get(named(Constant.LOCALE_SERVICES)),
                    get()
                )
            } bind LocaleRepository::class
        }
    }

    fun provideLocaleUseCase(): Module {
        return module {
            single { LocaleInteractor(get()) } bind LocaleUsecase::class
        }
    }
}