package com.agree.ecosystem.common.di

import android.app.Activity
import androidx.navigation.findNavController
import com.agree.ecosystem.common.R
import com.agree.ecosystem.common.presentation.navigation.MenuNavigation
import com.agree.ecosystem.common.presentation.navigation.MenuNavigationImpl
import org.koin.core.module.Module
import org.koin.dsl.module

interface NavigationModule {
    fun provideNavigationModule(): Array<Module> {
        return arrayOf(
            provideMenuNavigation()
        )
    }

    fun provideMenuNavigation(): Module {
        return module {
            factory<MenuNavigation> { (activity: Activity?) ->
                MenuNavigationImpl(activity?.findNavController(R.id.nav_host_fragment_menu), get())
            }
        }
    }
}
