package com.agree.ecosystem.auth.di

import android.app.Activity
import androidx.navigation.findNavController
import com.agree.ecosystem.auth.R
import com.agree.ecosystem.auth.presentation.navigation.AuthNavigation
import com.agree.ecosystem.auth.presentation.navigation.AuthNavigationImpl
import org.koin.core.module.Module
import org.koin.dsl.module

interface NavigationModule {
    fun provideNavigationModule(): Array<Module> {
        return arrayOf(
            provideAuthNavigation()
        )
    }

    fun provideAuthNavigation(): Module {
        return module {
            factory<AuthNavigation> { (activity: Activity?) ->
                AuthNavigationImpl(activity, activity?.findNavController(R.id.nav_host_auth))
            }
        }
    }
}
