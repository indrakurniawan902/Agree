package com.agree.ecosystem.common.di.modules.companies

import com.agree.ecosystem.common.data.reqres.CompaniesDataStore
import com.agree.ecosystem.common.data.reqres.CompaniesRepository
import com.agree.ecosystem.common.data.reqres.web.AgreeCompaniesApi
import com.agree.ecosystem.common.data.reqres.web.AgreeCompaniesApiClient
import com.agree.ecosystem.common.domain.reqres.CompaniesInteractor
import com.agree.ecosystem.common.domain.reqres.CompaniesUsecase
import com.agree.ecosystem.core.utils.utility.Constant
import com.agree.ecosystem.core.utils.utility.UrlManagement
import com.devbase.data.source.web.libs.createReactiveService
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Companies Module are modules that are responsible for injection of classes
 * related to Services, Repository and UseCase
 * @author: @telkomdev-abdul
 * @since: 04 Oct 2022
 */
interface CompaniesModule : ViewModelModule {
    fun provideCompaniesModule(): Array<Module> {
        return arrayOf(
            providePartnershipService(),
            providePartnershipRepository(),
            providePartnershipUseCase(),
            *provideCompaniesViewModel()
        )
    }

    fun providePartnershipService(): Module {
        return module {
            single(named(Constant.PARTNERSHIP_SERVICES)) {
                createReactiveService(
                    AgreeCompaniesApiClient::class.java,
                    get { parametersOf(false, true) },
                    UrlManagement.getBaseUrl()
                )
            }

            single { AgreeCompaniesApi(get(named(Constant.PARTNERSHIP_SERVICES))) }
        }
    }

    fun providePartnershipRepository(): Module {
        return module {
            single {
                CompaniesDataStore(null, get())
            } bind CompaniesRepository::class
        }
    }

    fun providePartnershipUseCase(): Module {
        return module {
            single {
                CompaniesInteractor(get())
            } bind CompaniesUsecase::class
        }
    }
}
