package com.agree.ecosystem.users.di

import android.content.Context
import androidx.startup.Initializer
import com.agree.ecosystem.core.utils.di.CoreUtilsModule
import com.agree.ecosystem.users.di.modules.FeatureModule
import org.koin.core.module.Module

class UsersModule :
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
