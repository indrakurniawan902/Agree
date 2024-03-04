package com.agree.ecosystem.agreepedia.di.modules.article

import com.agree.ecosystem.agreepedia.data.AgreepediaDataStore
import com.agree.ecosystem.agreepedia.data.AgreepediaRepository
import com.agree.ecosystem.agreepedia.data.web.AgreepediaApi
import com.agree.ecosystem.agreepedia.data.web.AgreepediaApiClient
import com.agree.ecosystem.agreepedia.domain.AgreepediaInteractor
import com.agree.ecosystem.agreepedia.domain.AgreepediaUsecase
import com.agree.ecosystem.core.utils.utility.Constant
import com.agree.ecosystem.core.utils.utility.UrlManagement
import com.devbase.data.source.web.libs.createReactiveService
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Article Module are modules that are responsible for injection of classes
 * related to Services, Repository and UseCase
 * @author: @telkomdev-abdul
 * @since: 26 Oct 2022
 */
interface ArticleModule : ViewModelModule {
    fun provideArticleModule(): Array<Module> {
        return arrayOf(
            provideArticleService(),
            provideArticleRepository(),
            provideArticleUseCase(),
            *provideArticleViewModel()
        )
    }

    fun provideArticleService(): Module {
        return module {
            single(named(Constant.AGREEPEDIA_SERVICES)) {
                createReactiveService(
                    AgreepediaApiClient::class.java,
                    get { parametersOf(false, true) },
                    UrlManagement.getAgreepediaBaseUrl()
                )
            }

            single { AgreepediaApi(get(named(Constant.AGREEPEDIA_SERVICES))) }
        }
    }

    fun provideArticleRepository(): Module {
        return module {
            single {
                AgreepediaDataStore(null, get())
            } bind AgreepediaRepository::class
        }
    }

    fun provideArticleUseCase(): Module {
        return module {
            single {
                AgreepediaInteractor(get())
            } bind AgreepediaUsecase::class
        }
    }
}
