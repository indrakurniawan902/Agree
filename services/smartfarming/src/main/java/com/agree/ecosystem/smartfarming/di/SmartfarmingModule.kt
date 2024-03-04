 package com.agree.ecosystem.smartfarming.di

import android.content.Context
import androidx.startup.Initializer
import com.agree.ecosystem.smartfarming.di.modules.FeatureModule
import org.koin.core.module.Module


class SmartfarmingModule : Initializer<Array<Module>>, FeatureModule {
    override fun create(context: Context): Array<Module> {
        return arrayOf(
            *provideFeatureModule()
        )
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}