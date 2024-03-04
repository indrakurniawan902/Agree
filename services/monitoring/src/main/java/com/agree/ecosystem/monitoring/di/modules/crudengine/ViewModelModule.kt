package com.agree.ecosystem.monitoring.di.modules.crudengine

import com.agree.ecosystem.monitoring.presentation.menu.additionalactivities.AdditionalActivitiesViewModel
import com.agree.ecosystem.monitoring.presentation.menu.cultivationactivities.CultivationActivitiesViewModel
import com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop.DetailActivitySopViewModel
import com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop.individual.IndividualMonitoringViewModel
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.activities.ActivitiesViewModel
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.dialog.addactivity.AddActivityViewModel
import com.agree.ecosystem.monitoring.presentation.menu.historyactivity.HistoryActivityViewModel
import com.agree.ecosystem.monitoring.presentation.menu.historyactivity.historyfilter.HistoryFilterViewModel
import com.agree.ecosystem.monitoring.presentation.menu.insertupdateadditionalactivitysop.InsertUpdateAdditionalActivitySopViewModel
import com.agree.ecosystem.monitoring.presentation.menu.phaseactivity.PhaseActivityViewModel
import com.agree.ecosystem.monitoring.presentation.menu.phaseactivity.subphaseactivity.SubPhaseActivityViewModel
import com.agree.ecosystem.monitoring.presentation.menu.totalactivitysop.TotalActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * ViewModel Module are modules that are responsible for injection of classes
 * related to ViewModels on CrudEngine Module
 * @author: @telkomdev-abdul
 * @since: 04 Oct 2022
 */
interface ViewModelModule {
    fun provideCrudEngineViewModel(): Array<Module> {
        return arrayOf(
            provideTotalActivity(),
            provideCultivationActivities(),
            provideHistoryActivity(),
            provideInsertAdditionalActivity(),
            provideAdditionalActivity(),
            providePhaseActivity(),
            provideSubPhaseActivity(),
            provideDetailActivity(),
            provideHistoryFilter(),
            provideAddActivity(),
            provideActivities(),
            provideIndividualMonitoring()
        )
    }

    fun provideTotalActivity(): Module {
        return module {
            viewModel { TotalActivityViewModel(get()) }
        }
    }

    fun provideCultivationActivities(): Module {
        return module {
            viewModel { CultivationActivitiesViewModel(get()) }
        }
    }

    fun provideHistoryActivity(): Module {
        return module {
            viewModel { HistoryActivityViewModel(get()) }
        }
    }

    fun provideInsertAdditionalActivity(): Module {
        return module {
            viewModel { InsertUpdateAdditionalActivitySopViewModel(get()) }
        }
    }

    fun provideAdditionalActivity(): Module {
        return module {
            viewModel { AdditionalActivitiesViewModel(get()) }
        }
    }

    fun providePhaseActivity(): Module {
        return module {
            viewModel { PhaseActivityViewModel(get()) }
        }
    }

    fun provideSubPhaseActivity(): Module {
        return module {
            viewModel { SubPhaseActivityViewModel(get()) }
        }
    }

    fun provideDetailActivity(): Module {
        return module {
            viewModel { DetailActivitySopViewModel(get()) }
        }
    }

    fun provideHistoryFilter(): Module {
        return module {
            viewModel { HistoryFilterViewModel(get()) }
        }
    }

    fun provideAddActivity(): Module {
        return module {
            viewModel { AddActivityViewModel(get()) }
        }
    }

    fun provideActivities(): Module {
        return module {
            viewModel { ActivitiesViewModel(get()) }
        }
    }

    fun provideIndividualMonitoring(): Module {
        return module {
            viewModel { IndividualMonitoringViewModel(get()) }
        }
    }
}
