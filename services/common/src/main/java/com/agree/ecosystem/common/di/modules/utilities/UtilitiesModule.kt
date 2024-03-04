package com.agree.ecosystem.common.di.modules.utilities

import com.agree.ecosystem.core.utils.utility.Constant
import com.agree.ecosystem.core.utils.utility.UrlManagement
import com.agree.ecosystem.utilities.data.reqres.UtilitiesDataStore
import com.agree.ecosystem.utilities.data.reqres.UtilitiesRepository
import com.agree.ecosystem.utilities.data.reqres.web.utilities.AgreeUtilitiesApi
import com.agree.ecosystem.utilities.data.reqres.web.utilities.AgreeUtilitiesApiClient
import com.agree.ecosystem.utilities.domain.reqres.UtilitiesInteractor
import com.agree.ecosystem.utilities.domain.reqres.UtilitiesUsecase
import com.devbase.data.source.web.libs.createReactiveService
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Utilities Module are modules that are responsible for injection of classes
 * related to Services, Repository and UseCase
 * @author: @telkomdev-abdul
 * @since: 04 Oct 2022
 */
interface UtilitiesModule : ViewModelModule {
    fun provideUtilitiesModule(): Array<Module> {
        return arrayOf(
            provideUtilitiesService(),
            provideUtilitiesRepository(),
            provideUtilitiesUseCase(),
            *provideUtilitiesViewModel()
        )
    }

    fun provideUtilitiesService(): Module {
        return module {
            single(named(Constant.UTILITIES_SERVICES)) {
                createReactiveService(
                    AgreeUtilitiesApiClient::class.java,
                    get { parametersOf(false, true) },
                    UrlManagement.getBaseUrl()
                )
            }
            single { AgreeUtilitiesApi(get(named(Constant.UTILITIES_SERVICES))) }
        }
    }

    fun provideUtilitiesRepository(): Module {
        return module {
            single {
                UtilitiesDataStore(get(), get())
            } bind UtilitiesRepository::class
        }
    }

    fun provideUtilitiesUseCase(): Module {
        return module {
            single {
                UtilitiesInteractor(get(), get())
            } bind UtilitiesUsecase::class
        }
    }
}
