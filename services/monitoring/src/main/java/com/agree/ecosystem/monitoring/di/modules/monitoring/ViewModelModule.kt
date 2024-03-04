package com.agree.ecosystem.monitoring.di.modules.monitoring

import com.agree.ecosystem.monitoring.presentation.menu.cultivationactivities.dateinput.SelectYearViewModel
import com.agree.ecosystem.monitoring.presentation.menu.detailarea.MonitoringDetailViewModel
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.DetailSubVesselViewModel
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident.AddIncidentViewModel
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident.IncidentViewModel
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident.report.IncidentReportViewModel
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselViewModel
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.transaction.TransactionViewModel
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.transaction.detail.TransactionDetailViewModel
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.transaction.insertupdate.InsertUpdateTransactionViewModel
import com.agree.ecosystem.monitoring.presentation.menu.historyactivity.historyfilter.HistoryMissedFilterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * ViewModel Module are modules that are responsible for injection of classes
 * related to ViewModels on Monitoring Module
 * @author: @telkomdev-abdul
 * @since: 04 Oct 2022
 */
interface ViewModelModule {
    fun provideMonitoringViewModel(): Array<Module> {
        return arrayOf(
            provideDetailSubVessel(),
            provideSelectYear(),
            provideIncidentReport(),
            provideAddIncident(),
            provideMonitoringDetail(),
            provideIncident(),
            provideSubVessel(),
            provideTransaction(),
            provideInsertTransaction(),
            provideTransactionDetail(),
            provideMissedFilterHistory()
        )
    }

    fun provideMissedFilterHistory(): Module {
        return module {
            viewModel { HistoryMissedFilterViewModel(get()) }
        }
    }

    fun provideDetailSubVessel(): Module {
        return module {
            viewModel { DetailSubVesselViewModel(get()) }
        }
    }

    fun provideSelectYear(): Module {
        return module {
            viewModel { SelectYearViewModel(get()) }
        }
    }

    fun provideIncidentReport(): Module {
        return module {
            viewModel { IncidentReportViewModel(get()) }
        }
    }

    fun provideAddIncident(): Module {
        return module {
            viewModel { AddIncidentViewModel(get()) }
        }
    }

    fun provideMonitoringDetail(): Module {
        return module {
            viewModel { MonitoringDetailViewModel(get()) }
        }
    }

    fun provideIncident(): Module {
        return module {
            viewModel { IncidentViewModel(get()) }
        }
    }

    fun provideSubVessel(): Module {
        return module {
            viewModel { SubVesselViewModel() }
        }
    }

    fun provideTransaction(): Module {
        return module {
            viewModel { TransactionViewModel(get()) }
        }
    }

    fun provideInsertTransaction(): Module {
        return module {
            viewModel { InsertUpdateTransactionViewModel(get()) }
        }
    }

    fun provideTransactionDetail(): Module {
        return module {
            viewModel { TransactionDetailViewModel(get()) }
        }
    }
}
