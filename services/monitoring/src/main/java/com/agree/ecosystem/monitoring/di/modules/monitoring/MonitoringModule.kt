package com.agree.ecosystem.monitoring.di.modules.monitoring

import com.agree.ecosystem.core.utils.utility.Constant
import com.agree.ecosystem.core.utils.utility.UrlManagement
import com.agree.ecosystem.core.utils.utility.createRoomDb
import com.agree.ecosystem.monitoring.data.MonitoringDatabase
import com.agree.ecosystem.monitoring.data.reqres.MonitoringDataStore
import com.agree.ecosystem.monitoring.data.reqres.MonitoringRepository
import com.agree.ecosystem.monitoring.data.reqres.db.MonitoringDb
import com.agree.ecosystem.monitoring.data.reqres.db.MonitoringDbImpl
import com.agree.ecosystem.monitoring.data.reqres.web.AgreeMonitoringApi
import com.agree.ecosystem.monitoring.data.reqres.web.AgreeMonitoringApiClient
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringInteractor
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.devbase.data.source.web.libs.createReactiveService
import org.koin.android.ext.koin.androidContext
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
            provideMonitoringDatabase(),
            provideMonitoringRepository(),
            provideMonitoringUseCase(),
            *provideMonitoringViewModel()
        )
    }

    fun provideMonitoringService(): Module {
        return module {
            single(named(Constant.MONITORING_NON_ENGINE_SERVICES)) {
                createReactiveService(
                    AgreeMonitoringApiClient::class.java,
                    get { parametersOf(false, true) },
                    UrlManagement.getBaseUrl()
                )
            }
            single { AgreeMonitoringApi(get(named(Constant.MONITORING_NON_ENGINE_SERVICES))) }
        }
    }

    fun provideMonitoringDatabase(): Module {
        return module {
            single { createRoomDb<MonitoringDatabase>(androidContext(), MonitoringDatabase.getDbName()) }
            single { MonitoringDbImpl(get()) } bind MonitoringDb::class
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
                MonitoringInteractor(get(), get())
            } bind MonitoringUseCase::class
        }
    }
}
