package com.agree.ecosystem.monitoring.di.modules

import com.agree.ecosystem.monitoring.di.modules.crudengine.CrudEngineModule
import com.agree.ecosystem.monitoring.di.modules.monitoring.MonitoringModule
import org.koin.core.module.Module

interface FeatureModule :
    MonitoringModule,
    CrudEngineModule {
    fun provideFeatureModule(): Array<Module> {
        return arrayOf(
            *provideCrudEngineModule(),
            *provideMonitoringModule()
        )
    }
}
