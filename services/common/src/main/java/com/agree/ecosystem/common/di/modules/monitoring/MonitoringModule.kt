package com.agree.ecosystem.common.di.modules.monitoring

import com.agree.ecosystem.common.data.reqres.MonitoringDataStore
import com.agree.ecosystem.common.data.reqres.MonitoringRepository
import com.agree.ecosystem.common.data.reqres.web.AgreeMonitoringApi
import com.agree.ecosystem.common.data.reqres.web.AgreeMonitoringApiClient
import com.agree.ecosystem.common.domain.reqres.MonitoringInteractor
import com.agree.ecosystem.common.domain.reqres.MonitoringUsecase
import com.agree.ecosystem.core.utils.utility.Constant
import com.agree.ecosystem.core.utils.utility.UrlManagement
import com.devbase.data.source.web.libs.createReactiveService
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Monitoring Module are modules that are responsible for injection of classes
 * related to Services, Repository and UseCase
 * @author: @telkomdev-abdul
 * @since: 04 Oct 2022
 */
interface MonitoringModule : ViewModelModule {
    fun provideMonitoringModule(): Array<Module> {
        return arrayOf(
            provideMonitoringService(),
            provideMonitoringRepository(),
            provideMonitoringUseCase(),
            *provideMonitoringViewModel()
        )
    }

    fun provideMonitoringService(): Module {
        return module {
            single(named(Constant.MONITORING_SERVICES)) {
                createReactiveService(
                    AgreeMonitoringApiClient::class.java,
                    get { parametersOf(false, true) },
                    UrlManagement.getBaseUrl()
                )
            }

            single { AgreeMonitoringApi(get(named(Constant.MONITORING_SERVICES))) }
        }
    }

    fun provideMonitoringRepository(): Module {
        return module {
            single {
                MonitoringDataStore(get(), get())
            } bind MonitoringRepository::class
        }
    }

    fun provideMonitoringUseCase(): Module {
        return module {
            single {
                MonitoringInteractor(get())
            } bind MonitoringUsecase::class
        }
    }
}
