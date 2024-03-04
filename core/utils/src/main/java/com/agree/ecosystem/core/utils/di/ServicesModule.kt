package com.agree.ecosystem.core.utils.di

import com.agree.ecosystem.core.utils.data.reqres.web.common.CommonApi
import com.agree.ecosystem.core.utils.data.reqres.web.common.CommonApiClient
import com.agree.ecosystem.core.utils.data.reqres.web.crudengine.EngineTokenApi
import com.agree.ecosystem.core.utils.data.reqres.web.crudengine.EngineTokenApiClient
import com.agree.ecosystem.core.utils.data.reqres.web.token.AgreeTokenApi
import com.agree.ecosystem.core.utils.data.reqres.web.token.AgreeTokenApiClient
import com.agree.ecosystem.core.utils.utility.Constant
import com.agree.ecosystem.core.utils.utility.UrlManagement
import com.devbase.data.source.web.libs.createReactiveService
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Services Injection Module are modules that are responsible for injection of classes
 * related to Api Services and Retrofit Instance
 * @author: @telkomdev-abdul
 * @since: 3 Oct 2022
 *
 * Please Declare Services name on Constant!
 */
interface ServicesModule {

    fun provideServices(): Array<Module> {
        return arrayOf(
            provideServiceCommon(),
            provideServiceEngineToken(),
            provideServiceToken()
        )
    }

    fun provideServiceCommon(): Module {
        return module {
            single(named(Constant.COMMON_SERVICES)) {
                createReactiveService(
                    CommonApiClient::class.java,
                    get { parametersOf(false, false) },
                    UrlManagement.getBaseUrl()
                )
            }

            single { CommonApi(get(named(Constant.COMMON_SERVICES))) }
        }
    }

    fun provideServiceEngineToken(): Module {
        return module {
            single(named(Constant.ENGINE_TOKEN_SERVICES)) {
                createReactiveService(
                    EngineTokenApiClient::class.java,
                    get { parametersOf(false, false) },
                    UrlManagement.getBaseUrl()
                )
            }

            single { EngineTokenApi(get(named(Constant.ENGINE_TOKEN_SERVICES))) }
        }
    }

    fun provideServiceToken(): Module {
        return module {
            single(named(Constant.TOKEN_SERVICES)) {
                createReactiveService(
                    AgreeTokenApiClient::class.java,
                    get { parametersOf(false, false) },
                    UrlManagement.getBaseUrl()
                )
            }

            single { AgreeTokenApi(get(named(Constant.TOKEN_SERVICES))) }
        }
    }
}
