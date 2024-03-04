package com.agree.ecosystem.core.utils.di

import com.agree.ecosystem.core.utils.data.reqres.AgrPrefDataStore
import com.agree.ecosystem.core.utils.data.reqres.AgrPrefRepository
import com.agree.ecosystem.core.utils.data.reqres.sharedprefs.AgrPrefsManager
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefInteractor
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.Constant
import com.agree.ecosystem.core.utils.utility.eventbus.EventBus
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

interface OtherModule {
    fun provideOtherModule(): Array<Module> {
        return arrayOf(
            provideEventBus(),
            provideSharedPrefs()
        )
    }

    fun provideEventBus(): Module {
        return module {
            single { EventBus() }
        }
    }

    fun provideSharedPrefs(): Module {
        return module {
            single<AgrPrefRepository> {
                AgrPrefDataStore(
                    AgrPrefsManager(
                        androidContext(),
                        Constant.PREF_NAME,
                        Gson()
                    )
                )
            }

            single<AgrPrefUsecase> {
                AgrPrefInteractor(get())
            }
        }
    }
}
