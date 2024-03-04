package com.agree.ecosystem.core.analytics.di

import com.agree.ecosystem.core.analytics.AgrAnalytics
import com.agree.ecosystem.core.analytics.FirebaseAnalytics
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

interface OtherModule {
    fun provideOtherModule(): Array<Module> {
        return arrayOf(
            provideAnalytics()
        )
    }

    fun provideAnalytics(): Module {
        return module {
            single {
                FirebaseAnalytics()
            } bind AgrAnalytics::class
        }
    }
}
