package com.agree.ecosystem.monitoring.di.modules.crudengine

import com.agree.ecosystem.core.utils.utility.Constant
import com.agree.ecosystem.core.utils.utility.UrlManagement
import com.agree.ecosystem.monitoring.data.reqres.CrudEngineDataStore
import com.agree.ecosystem.monitoring.data.reqres.CrudEngineRepository
import com.agree.ecosystem.monitoring.data.reqres.web.AgreeMonitoringEngineApi
import com.agree.ecosystem.monitoring.data.reqres.web.AgreeMonitoringEngineApiClient
import com.devbase.data.source.web.libs.createReactiveService
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * CrudEngine Module are modules that are responsible for injection of classes
 * related to Services, Repository and UseCase
 * @author: @telkomdev-abdul
 * @since: 04 Oct 2022
 */
interface CrudEngineModule : ViewModelModule {
    fun provideCrudEngineModule(): Array<Module> {
        return arrayOf(
            provideCrudEngineService(),
            provideCrudEngineRepository(),
            provideCrudEngineUseCase(),
            *provideCrudEngineViewModel()
        )
    }

    fun provideCrudEngineService(): Module {
        return module {
            single(named(Constant.MONITORING_ENGINE_SERVICES)) {
                createReactiveService(
                    AgreeMonitoringEngineApiClient::class.java,
                    get { parametersOf(false, true) },
                    UrlManagement.getBaseUrl()
                )
            }

            single { AgreeMonitoringEngineApi(get(named(Constant.MONITORING_ENGINE_SERVICES))) }
        }
    }

    fun provideCrudEngineRepository(): Module {
        return module {
            singleOf(::CrudEngineDataStore) bind CrudEngineRepository::class
        }
    }

    fun provideCrudEngineUseCase(): Module {
        return module {
        }
    }
}
