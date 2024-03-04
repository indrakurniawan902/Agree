package com.agree.ecosystem.users.di.modules

import com.agree.ecosystem.users.di.modules.profile.ProfileModule
import org.koin.core.module.Module

interface FeatureModule :
    ProfileModule {
    fun provideFeatureModule(): Array<Module> {
        return arrayOf(
            *provideProfileModule()
        )
    }
}
