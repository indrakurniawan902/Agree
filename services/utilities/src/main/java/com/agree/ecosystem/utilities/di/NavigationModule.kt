package com.agree.ecosystem.utilities.di

import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.agree.ecosystem.utilities.R
import com.agree.ecosystem.utilities.presentation.navigation.MainNavigation
import com.agree.ecosystem.utilities.presentation.navigation.MainNavigationImpl
import org.koin.core.module.Module
import org.koin.dsl.bind
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
            factory { (activity: FragmentActivity?) ->
                MainNavigationImpl(activity?.findNavController(R.id.nav_host_utilities), activity)
            } bind MainNavigation::class
        }
    }
}
