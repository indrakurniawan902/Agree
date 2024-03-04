package com.agree.ecosystem.auth.di

import android.content.Context
import androidx.startup.Initializer
import com.agree.ecosystem.auth.di.modules.FeatureModule
import com.agree.ecosystem.core.utils.di.CoreUtilsModule
import org.koin.core.module.Module

class AuthModule :
    Initializer<Array<Module>>,
    FeatureModule,
    NavigationModule {
    override fun create(context: Context): Array<Module> {
        return arrayOf(
            *provideFeatureModule(),
            *provideNavigationModule()
        )
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            CoreUtilsModule::class.java
        )
    }
}
