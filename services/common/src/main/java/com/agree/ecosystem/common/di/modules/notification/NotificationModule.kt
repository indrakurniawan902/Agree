package com.agree.ecosystem.common.di.modules.notification

import com.agree.ecosystem.common.data.reqres.NotificationDataStore
import com.agree.ecosystem.common.data.reqres.NotificationRepository
import com.agree.ecosystem.common.data.reqres.web.AgreeNotificationApi
import com.agree.ecosystem.common.data.reqres.web.AgreeNotificationApiClient
import com.agree.ecosystem.common.domain.reqres.NotificationInteractor
import com.agree.ecosystem.common.domain.reqres.NotificationUseCase
import com.agree.ecosystem.core.utils.utility.Constant
import com.agree.ecosystem.core.utils.utility.UrlManagement
import com.devbase.data.source.web.libs.createReactiveService
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Notification Module are modules that are responsible for injection of classes
 * related to Services, Repository and UseCase
 * @author: @telkomdev-abdul
 * @since: 04 Oct 2022
 */
interface NotificationModule : ViewModelModule {
    fun provideNotificationModule(): Array<Module> {
        return arrayOf(
            provideNotificationService(),
            provideNotificationRepository(),
            provideNotificationUseCase(),
            *provideNotificationViewModel()
        )
    }

    fun provideNotificationService(): Module {
        return module {
            single(named(Constant.NOTIFCATION_SERVICES)) {
                createReactiveService(
                    AgreeNotificationApiClient::class.java,
                    get { parametersOf(false, true) },
                    UrlManagement.getBaseUrl()
                )
            }
            single { AgreeNotificationApi(get(named(Constant.NOTIFCATION_SERVICES))) }
        }
    }

    fun provideNotificationRepository(): Module {
        return module {
            single {
                NotificationDataStore(null, get())
            } bind NotificationRepository::class
        }
    }

    fun provideNotificationUseCase(): Module {
        return module {
            single {
                NotificationInteractor(get())
            } bind NotificationUseCase::class
        }
    }
}
