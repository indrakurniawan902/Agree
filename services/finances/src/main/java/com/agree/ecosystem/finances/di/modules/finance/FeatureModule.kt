package com.agree.ecosystem.finances.di.modules.finance

import com.agree.ecosystem.partnership.di.modules.partnership.PartnershipModule
import org.koin.core.module.Module

interface FeatureModule : FinanceModule, PartnershipModule {
    fun provideFeatureModule(): Array<Module> {
        return arrayOf(
            *provideFinanceModule(),
            *providePartnershipModule()
        )
    }
}