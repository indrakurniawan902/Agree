package com.agree.ecosystem.utilities.di.modules.zone

import com.agree.ecosystem.utilities.presentation.menu.zone.district.DistrictViewModel
import com.agree.ecosystem.utilities.presentation.menu.zone.province.ProvinceViewModel
import com.agree.ecosystem.utilities.presentation.menu.zone.subdistrict.SubDistrictViewModel
import com.agree.ecosystem.utilities.presentation.menu.zone.village.VillageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * ViewModel Module are modules that are responsible for injection of classes
 * related to ViewModels on Zone Module
 * @author: @telkomdev-abdul
 * @since: 17 Dec 2022
 */
interface ViewModelModule {
    fun provideZoneViewModel(): Array<Module> {
        return arrayOf(
            provideProvince(),
            provideDistrict(),
            provideSubDistrict(),
            provideVillage()
        )
    }

    fun provideProvince(): Module {
        return module {
            viewModel { ProvinceViewModel(get()) }
        }
    }

    fun provideDistrict(): Module {
        return module {
            viewModel { DistrictViewModel(get()) }
        }
    }

    fun provideSubDistrict(): Module {
        return module {
            viewModel { SubDistrictViewModel(get()) }
        }
    }

    fun provideVillage(): Module {
        return module {
            viewModel { VillageViewModel(get()) }
        }
    }
}
