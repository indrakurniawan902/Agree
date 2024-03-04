package com.agree.ecosystem.common.di.modules.notification
import com.agree.ecosystem.common.presentation.menu.notifications.NotificationViewModel
import com.agree.ecosystem.common.presentation.menu.notifications.detail.DetailNotificationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * ViewModel Module are modules that are responsible for injection of classes
 * related to ViewModels on Notification Module
 * @author: @telkomdev-abdul
 * @since: 04 Oct 2022
 */

interface ViewModelModule {
    fun provideNotificationViewModel(): Array<Module> {
        return arrayOf(
            provideNotification(),
            provideDetailNotification()
        )
    }

    fun provideNotification(): Module {
        return module {
            viewModel {
                NotificationViewModel(get())
            }
        }
    }

    fun provideDetailNotification(): Module {
        return module {
            viewModel {
                DetailNotificationViewModel(get())
            }
        }
    }
}
