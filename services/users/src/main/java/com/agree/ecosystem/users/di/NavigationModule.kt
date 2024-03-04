package com.agree.ecosystem.users.di

import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.agree.ecosystem.users.R
import com.agree.ecosystem.users.presentation.navigation.MainNavigation
import com.agree.ecosystem.users.presentation.navigation.MainNavigationImpl
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

interface NavigationModule {
    fun provideNavigationModule(): Array<Module> {
        return arrayOf(
            provideMainNavigation()
        )
    }

    fun provideMainNavigation(): Module {
        return module {
            factory { (activity: FragmentActivity?) ->
                MainNavigationImpl(
                    activity?.findNavController(R.id.nav_host_profile),
                    activity
                )
            } bind MainNavigation::class
        }
    }
}
