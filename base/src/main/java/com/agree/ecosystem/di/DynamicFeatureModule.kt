package com.agree.ecosystem.di

import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

interface DynamicFeatureModule {
    fun provideDynamicFeatureModule(): Module {
        return module {
            factory {
                SplitInstallManagerFactory.create(androidContext())
            }
        }
    }
}
