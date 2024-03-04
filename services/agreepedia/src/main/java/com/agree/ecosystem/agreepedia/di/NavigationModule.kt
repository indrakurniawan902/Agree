package com.agree.ecosystem.agreepedia.di

import android.app.Activity
import androidx.navigation.findNavController
import com.agree.ecosystem.agreepedia.R
import com.agree.ecosystem.agreepedia.presentation.navigation.MainNavigation
import com.agree.ecosystem.agreepedia.presentation.navigation.MainNavigationImpl
import org.koin.core.module.Module
import org.koin.dsl.module

interface NavigationModule {
    fun provideNavigationModule(): Array<Module> {
        return arrayOf(
            provideMainNavigation()
        )
    }

    /**
     * Navigation for Main Activity
     */
    fun provideMainNavigation(): Module {
        return module {
            factory<MainNavigation> { (activity: Activity?) ->
                MainNavigationImpl(activity?.findNavController(R.id.nav_host_agreepedia), activity)
            }
        }
    }
}
