package com.agree.ecosystem.common.di.modules

import com.agree.ecosystem.common.di.modules.companies.CompaniesModule
import com.agree.ecosystem.common.di.modules.finance.FinanceModule
import com.agree.ecosystem.common.di.modules.home.HomeModule
import com.agree.ecosystem.common.di.modules.monitoring.MonitoringModule
import com.agree.ecosystem.common.di.modules.notification.NotificationModule
import com.agree.ecosystem.common.di.modules.utilities.UtilitiesModule
import org.koin.core.module.Module

interface FeatureModule :
    NotificationModule,
    HomeModule,
    UtilitiesModule,
    CompaniesModule,
    MonitoringModule,
    FinanceModule {
    fun provideFeatureModule(): Array<Module> {
        return arrayOf(
            *provideNotificationModule(),
            *provideUtilitiesModule(),
            *provideCompaniesModule(),
            *provideMonitoringModule(),
            *provideHomeModule(),
            *provideFinanceModule()
        )
    }
}
