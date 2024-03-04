package com.agree.ecosystem.common.di.modules.monitoring

import com.agree.ecosystem.common.presentation.menu.monitoring.subvessels.SubVesselViewModel
import com.agree.ecosystem.common.presentation.menu.monitoring.vessels.VesselViewModel
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
            provideVessel(),
            provideSubVessel()
        )
    }

    fun provideSubVessel(): Module {
        return module {
            viewModel {
                SubVesselViewModel(get())
            }
        }
    }

    fun provideVessel(): Module {
        return module {
            viewModel {
                VesselViewModel(get())
            }
        }
    }
}
