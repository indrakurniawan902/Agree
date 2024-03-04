package com.agree.ecosystem.partnership.di

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.agree.ecosystem.partnership.R
import com.agree.ecosystem.partnership.presentation.navigation.*
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

interface NavigationModule {
    fun provideNavigationModule(): Array<Module> {
        return arrayOf(
            provideRegistrationNavigation(),
            provideStatusRegisterNavigation(),
            provideDetailStatusNavigation()
        )
    }

    fun provideRegistrationNavigation(): Module {
        return module {
            factory { (activity: FragmentActivity?) ->
                RegistrationNavigationImpl(
                    activity?.findNavController(R.id.nav_host_register_partnership),
                    activity
                )
            } bind RegistrationNavigation::class
        }
    }

    fun provideDetailStatusNavigation(): Module {
        return module {
            factory { (activity: Activity?) ->
                StatusRegisterNavigationImpl(activity?.findNavController(R.id.nav_host_status_register))
            } bind StatusRegisterNavigation::class
        }
    }

    fun provideStatusRegisterNavigation(): Module {
        return module {
            factory { (activity: Activity?) ->
                ListStatusRegisterNavigationImpl(
                    activity?.findNavController(R.id.nav_host_list_status_register),
                    activity
                )
            } bind ListStatusRegisterNavigation::class
        }
    }
}
