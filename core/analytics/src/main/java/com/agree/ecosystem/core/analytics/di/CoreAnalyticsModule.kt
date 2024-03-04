package com.agree.ecosystem.core.analytics.di

import android.content.Context
import androidx.startup.Initializer
import com.agree.ecosystem.core.analytics.di.modules.FeatureModule
import org.koin.core.module.Module

class CoreAnalyticsModule :
    Initializer<Array<Module>>,
    FeatureModule,
    OtherModule {
    override fun create(context: Context): Array<Module> {
        return arrayOf(
            *provideFeatureModule(),
            *provideOtherModule()
        )
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}
