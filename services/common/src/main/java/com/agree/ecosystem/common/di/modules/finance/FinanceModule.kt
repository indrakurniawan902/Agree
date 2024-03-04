package com.agree.ecosystem.common.di.modules.finance

import com.agree.ecosystem.common.data.reqres.FinanceDataStore
import com.agree.ecosystem.common.data.reqres.FinanceRepository
import com.agree.ecosystem.common.data.reqres.web.AgreeFinanceApi
import com.agree.ecosystem.common.data.reqres.web.AgreeFinanceApiClient
import com.agree.ecosystem.common.domain.reqres.FinanceInteractor
import com.agree.ecosystem.common.domain.reqres.FinanceUseCase
import com.agree.ecosystem.core.utils.utility.Constant
import com.agree.ecosystem.core.utils.utility.UrlManagement
import com.devbase.data.source.web.libs.createReactiveService
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

interface FinanceModule : ViewModelModule {
    fun provideFinanceModule(): Array<Module> {
        return arrayOf(
            provideFinanceService(),
            provideFinanceRepository(),
            provideFinanceUseCase(),
            *provideFinanceViewModel()
        )
    }

    fun provideFinanceService(): Module {
        return module {
            single(named(Constant.FINANCE_SERVICES)) {
                createReactiveService(
                    AgreeFinanceApiClient::class.java,
                    get { parametersOf(false, true) },
                    UrlManagement.getBaseUrl()
                )
            }
            single { AgreeFinanceApi(get(named(Constant.FINANCE_SERVICES))) }
        }
    }

    fun provideFinanceRepository(): Module {
        return module {
            single { FinanceDataStore(null, get()) } bind FinanceRepository::class
        }
    }

    fun provideFinanceUseCase(): Module {
        return module {
            single { FinanceInteractor(get(), get()) } bind FinanceUseCase::class
        }
    }
}
