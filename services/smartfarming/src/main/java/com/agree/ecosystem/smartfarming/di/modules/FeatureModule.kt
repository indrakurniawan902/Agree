package com.agree.ecosystem.smartfarming.di.modules

import com.agree.ecosystem.smartfarming.di.modules.smartfarming.SmartFarmingModule
import org.koin.core.module.Module

interface FeatureModule : SmartFarmingModule {
    fun provideFeatureModule(): Array<Module> {
        return arrayOf(
            *provideSmartFarmingModule()
        )
    }
}

