package com.agree.ui.di

import org.koin.core.module.Module

/**
 * Repository Injection Module are modules that are responsible for injection of classes
 * related to Repository and DataStore
 * @author: @telkomdev-abdul
 * @since: 30 June 2023
 */
interface RepositoryModule {
    /**
     * Group all module into array
     */
    fun provideRepositories(): Array<Module> {
        return arrayOf()
    }
}
