package com.agree.ecosystem.monitoring.di

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.presentation.navigation.*
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

interface NavigationModule {
    fun provideNavigationModule(): Array<Module> {
        return arrayOf(
            provideActivitySopNavigation(),
            provideMonitoringNavigation(),
            provideDetailSubVesselNavigation(),
            provideDetailActivitySopNavigation()
        )
    }

    fun provideActivitySopNavigation(): Module {
        return module {
            factory { (activity: FragmentActivity?) ->
                ActivitySopNavigationImpl(
                    activity?.findNavController(R.id.nav_host_activity_sop),
                    activity
                )
            } bind ActivitySopNavigation::class
        }
    }

    fun provideDetailSubVesselNavigation(): Module {
        return module {
            factory { (activity: Activity?) ->
                DetailSubVesselNavigationImpl(
                    activity?.findNavController(R.id.nav_host_detail_subvessel),
                    activity
                )
            } bind DetailSubVesselNavigation::class
        }
    }

    fun provideMonitoringNavigation(): Module {
        return module {
            factory { (activity: FragmentActivity?) ->
                MonitoringNavigationImpl(
                    activity?.findNavController(R.id.nav_host_monitoring),
                    activity
                )
            } bind MonitoringNavigation::class
        }
    }

    fun provideDetailActivitySopNavigation(): Module {
        return module {
            factory { (activity: Activity?) ->
                DetailActivitySopNavigationImpl(
                    activity?.findNavController(R.id.nav_host_detail_activity_sop),
                    activity
                )
            } bind DetailActivitySopNavigation::class
        }
    }
}
