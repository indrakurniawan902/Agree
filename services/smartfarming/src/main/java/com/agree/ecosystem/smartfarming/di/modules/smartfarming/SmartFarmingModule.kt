package com.agree.ecosystem.smartfarming.di.modules.smartfarming

import com.agree.ecosystem.core.utils.utility.Constant
import com.agree.ecosystem.core.utils.utility.UrlManagement
import com.agree.ecosystem.smartfarming.data.reqres.SmartFarmingDataStore
import com.agree.ecosystem.smartfarming.data.reqres.SmartFarmingRepository
import com.agree.ecosystem.smartfarming.data.reqres.web.SmartFarmingApi
import com.agree.ecosystem.smartfarming.data.reqres.web.SmartFarmingApiClient
import com.agree.ecosystem.smartfarming.domain.reqres.SmartFarmingInteractor
import com.agree.ecosystem.smartfarming.domain.reqres.SmartFarmingUsecase
import com.devbase.data.source.web.libs.createReactiveService
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

interface SmartFarmingModule : ViewModelModule {
    fun provideSmartFarmingModule(): Array<Module> {
        return arrayOf(
            provideSmartFarmingService(),
            provideSmartFarmingRepository(),
            provideSmartFarmingUseCase(),
            *provideSmartFarmingViewModel()
        )
    }

    fun provideSmartFarmingService(): Module {
        return module {
            single(named(Constant.SMART_FARMING_SERVICES)) {
                createReactiveService(
                    SmartFarmingApiClient::class.java,
                    get { parametersOf(false, true) },
                    UrlManagement.getBaseUrl()
                )
            }

            single { SmartFarmingApi(get(named(Constant.SMART_FARMING_SERVICES))) }
        }
    }

    fun provideSmartFarmingRepository(): Module {
        return module {
            single {
                SmartFarmingDataStore(null, get())
            } bind SmartFarmingRepository::class
        }
    }

    fun provideSmartFarmingUseCase(): Module {
        return module {
            single {
                SmartFarmingInteractor(get())
            } bind SmartFarmingUsecase::class
        }
    }
}
