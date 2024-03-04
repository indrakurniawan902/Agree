package com.agree.ecosystem.partnership.di.modules

import com.agree.ecosystem.partnership.di.modules.partnership.PartnershipModule
import org.koin.core.module.Module

interface FeatureModule :
    PartnershipModule {
    fun provideFeatureModule(): Array<Module> {
        return arrayOf(
            *providePartnershipModule()
        )
    }
}
