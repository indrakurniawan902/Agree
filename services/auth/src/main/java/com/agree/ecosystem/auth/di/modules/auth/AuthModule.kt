package com.agree.ecosystem.auth.di.modules.auth

import com.agree.ecosystem.auth.data.reqres.AuthDataStore
import com.agree.ecosystem.auth.data.reqres.AuthRepository
import com.agree.ecosystem.auth.data.reqres.web.AgreeAuthApi
import com.agree.ecosystem.auth.data.reqres.web.AgreeAuthApiClient
import com.agree.ecosystem.auth.domain.reqres.AuthInteractor
import com.agree.ecosystem.auth.domain.reqres.AuthUsecase
import com.agree.ecosystem.core.utils.utility.Constant
import com.agree.ecosystem.core.utils.utility.UrlManagement
import com.devbase.data.source.web.libs.createReactiveService
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Auth Module are modules that are responsible for injection of classes
 * related to Services, Repository and UseCase
 * @author: @telkomdev-abdul
 * @since: 04 Oct 2022
 */
interface AuthModule : ViewModelModule {
    fun provideAuthModule(): Array<Module> {
        return arrayOf(
            provideAuthService(),
            provideAuthRepository(),
            provideAuthUseCase(),
            *provideAuthViewModel()
        )
    }

    fun provideAuthService(): Module {
        return module {
            single(named(Constant.AUTH_SERVICES)) {
                createReactiveService(
                    AgreeAuthApiClient::class.java,
                    get { parametersOf(false, false) },
                    UrlManagement.getBaseUrl()
                )
            }

            single {
                AgreeAuthApi(
                    get(named(Constant.AUTH_SERVICES))
                )
            }
        }
    }

    fun provideAuthRepository(): Module {
        return module {
            single {
                AuthDataStore(null, get(), get())
            } bind AuthRepository::class
        }
    }

    fun provideAuthUseCase(): Module {
        return module {
            single {
                AuthInteractor(get())
            } bind AuthUsecase::class
        }
    }
}
