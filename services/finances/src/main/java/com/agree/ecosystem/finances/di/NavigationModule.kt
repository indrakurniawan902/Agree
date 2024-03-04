package com.agree.ecosystem.finances.di

import android.app.Activity
import androidx.navigation.findNavController
import com.agree.ecosystem.finances.R
import com.agree.ecosystem.finances.presentation.navigation.DataCultivatorNaviationImpl
import com.agree.ecosystem.finances.presentation.navigation.DataCultivatorNavigation
import com.agree.ecosystem.finances.presentation.navigation.LoanSubmissionImpl
import com.agree.ecosystem.finances.presentation.navigation.LoanSubmissionNavigation
import com.agree.ecosystem.finances.presentation.navigation.MenuNavigation
import com.agree.ecosystem.finances.presentation.navigation.MenuNavigationImpl
import com.agree.ecosystem.finances.presentation.navigation.ProfileCultivatorNavigation
import com.agree.ecosystem.finances.presentation.navigation.ProfileCultivatorNavigationImpl
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

interface NavigationModule {

    fun provideNavigationModule(): Array<Module> {
        return arrayOf(
            provideDataCultivatorNavigation(),
            providePartnershipNavigation(),
            provideLoanSubmissionNavigation(),
            provideCultivatorProfileNavigation()
        )
    }

    fun provideDataCultivatorNavigation(): Module {
        return module {
            factory { (activity: Activity?) ->
                DataCultivatorNaviationImpl(
                    activity?.findNavController(R.id.nav_host_activity_finance),
                    activity
                )
            } bind DataCultivatorNavigation::class
        }
    }

    fun providePartnershipNavigation(): Module {
        return module {
            factory { (activity: Activity) ->
                MenuNavigationImpl(
                    activity.findNavController(R.id.nav_host_activity_finance),
                    activity
                )
            } bind MenuNavigation::class
        }
    }

    fun provideLoanSubmissionNavigation(): Module {
        return module {
            factory { (activity: Activity) ->
                LoanSubmissionImpl(
                    activity.findNavController(R.id.nav_submission_loan),
                    activity
                )
            } bind LoanSubmissionNavigation::class
        }
    }

    fun provideCultivatorProfileNavigation(): Module {
        return module {
            factory { (activity: Activity?) ->
                ProfileCultivatorNavigationImpl(
                    activity?.findNavController(R.id.nav_host_data_profil_cultivator),
                    activity
                )
            } bind ProfileCultivatorNavigation::class
        }
    }
}
