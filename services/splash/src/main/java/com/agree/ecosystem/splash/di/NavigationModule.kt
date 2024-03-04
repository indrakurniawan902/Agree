package com.agree.ecosystem.splash.di

import android.app.Activity
import androidx.navigation.findNavController
import com.agree.ecosystem.splash.R
import com.agree.ecosystem.splash.presentation.navigation.SplashNavigation
import com.agree.ecosystem.splash.presentation.navigation.SplashNavigationImpl
import org.koin.core.module.Module
import org.koin.dsl.module

interface NavigationModule {
    fun provideNavigationModule(): Array<Module> {
        return arrayOf(
            provideSplashNavigation()
        )
    }

    fun provideSplashNavigation(): Module {
        return module {
            factory<SplashNavigation> { (activity: Activity?) ->
                SplashNavigationImpl(activity, activity?.findNavController(R.id.nav_host_splash), get())
            }
        }
    }
}
