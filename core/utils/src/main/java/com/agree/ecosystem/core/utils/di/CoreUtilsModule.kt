package com.agree.ecosystem.core.utils.di

import android.content.Context
import androidx.startup.Initializer
import com.agree.ecosystem.core.analytics.di.CoreAnalyticsModule
import com.agree.ui.di.CoreUiModule
import org.koin.core.module.Module

/**
 * Wrapping All Module are modules that are responsible for injection of classes
 * related to all modules in Utils Module
 * @author: @telkomdev-abdul
 * @since: 3 Oct 2022
 */
class CoreUtilsModule :
    Initializer<Array<Module>>,
    OtherModule,
    ApiModule,
    ServicesModule,
    RepositoryModule,
    UseCaseModule,
    ViewModelModule {

    override fun create(context: Context): Array<Module> {
        return arrayOf(
            *provideOtherModule(),
            *provideApiModules(),
            *provideServices(),
            *provideRepositories(),
            *provideUseCases(),
            *provideViewModels()
        )
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            CoreAnalyticsModule::class.java,
            CoreUiModule::class.java
        )
    }
}
