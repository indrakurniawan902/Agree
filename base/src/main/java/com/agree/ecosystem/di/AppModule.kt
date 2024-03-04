package com.agree.ecosystem.di

import android.content.Context
import androidx.startup.Initializer
import com.agree.ecosystem.core.utils.di.CoreUtilsModule
import org.koin.core.module.Module

/**
 * Wrapping All Module are modules that are responsible for injection of classes
 * related to all modules in Base Module
 * @author @telkomdev-abdul
 * @since 4 Oct 2022
 */
class AppModule :
    Initializer<Array<Module>>,
    DynamicFeatureModule {

    override fun create(context: Context): Array<Module> {
        return arrayOf(
            provideDynamicFeatureModule()
        )
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            CoreUtilsModule::class.java
        )
    }
}
