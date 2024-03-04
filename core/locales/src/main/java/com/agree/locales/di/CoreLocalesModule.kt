package com.agree.locales.di

import android.content.Context
import androidx.startup.Initializer
import com.agree.ecosystem.core.utils.di.CoreUtilsModule
import com.agree.locales.di.modules.FeatureModule
import org.koin.core.module.Module


class CoreLocalesModule : Initializer<Array<Module>>,
    FeatureModule {
    override fun create(context: Context): Array<Module> {
        return arrayOf(
            *provideFeatureModule()
        )
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            CoreUtilsModule::class.java
        )
    }
}