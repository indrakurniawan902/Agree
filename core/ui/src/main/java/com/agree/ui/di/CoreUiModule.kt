package com.agree.ui.di

import android.content.Context
import androidx.startup.Initializer
import org.koin.core.module.Module

/**
 * Wrapping All Module are modules that are responsible for injection of classes
 * related to all modules in Utils Module
 * @author: @telkomdev-abdul
 * @since: 30 June 2023
 */
class CoreUiModule :
    Initializer<Array<Module>>,
    DatabaseModule,
    RepositoryModule {
    override fun create(context: Context): Array<Module> {
        return arrayOf(
            provideDatabase(),
            *provideRepositories()
        )
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}
