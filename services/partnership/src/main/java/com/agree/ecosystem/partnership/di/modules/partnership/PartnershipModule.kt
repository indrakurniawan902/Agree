package com.agree.ecosystem.partnership.di.modules.partnership

import com.agree.ecosystem.core.utils.utility.Constant
import com.agree.ecosystem.core.utils.utility.UrlManagement
import com.agree.ecosystem.partnership.data.reqres.PartnershipDataStore
import com.agree.ecosystem.partnership.data.reqres.PartnershipRepository
import com.agree.ecosystem.partnership.data.reqres.web.AgreePartnershipApi
import com.agree.ecosystem.partnership.data.reqres.web.AgreePartnershipApiClient
import com.agree.ecosystem.partnership.domain.reqres.PartnershipInteractor
import com.agree.ecosystem.partnership.domain.reqres.PartnershipUsecase
import com.devbase.data.source.web.libs.createReactiveService
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Partnership Module are modules that are responsible for injection of classes
 * related to Services, Repository and UseCase
 * @author: @telkomdev-abdul
 * @since: 04 Oct 2022
 */
interface PartnershipModule : ViewModelModule {
    fun providePartnershipModule(): Array<Module> {
        return arrayOf(
            providePartnershipService(),
            providePartnershipRepository(),
            providePartnershipUseCase(),
            *providePartnershipViewModel()
        )
    }

    fun providePartnershipService(): Module {
        return module {
            single(named(Constant.PARTNERSHIP_SERVICES)) {
                createReactiveService(
                    AgreePartnershipApiClient::class.java,
                    get { parametersOf(false, true) },
                    UrlManagement.getBaseUrl()
                )
            }

            single { AgreePartnershipApi(get(named(Constant.PARTNERSHIP_SERVICES))) }
        }
    }

    fun providePartnershipRepository(): Module {
        return module {
            single {
                PartnershipDataStore(null, get())
            } bind PartnershipRepository::class
        }
    }

    fun providePartnershipUseCase(): Module {
        return module {
            single {
                PartnershipInteractor(get())
            } bind PartnershipUsecase::class
        }
    }
}
