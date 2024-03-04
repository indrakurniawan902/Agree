package com.agree.ecosystem.utilities.di.modules

import com.agree.ecosystem.utilities.di.modules.utilities.UtilitiesModule
import com.agree.ecosystem.utilities.di.modules.zone.ZoneModule
import org.koin.core.module.Module

interface FeatureModule :
    ZoneModule,
    UtilitiesModule {
    fun provideFeatureModule(): Array<Module> {
        return arrayOf(
            *provideUtilitiesModule(),
            *provideZoneModule()
        )
    }
}
