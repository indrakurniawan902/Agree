package com.agree.ecosystem.utilities.di.modules.zone

import com.agree.ecosystem.core.utils.utility.Constant
import com.agree.ecosystem.core.utils.utility.UrlManagement
import com.agree.ecosystem.core.utils.utility.createRoomDb
import com.agree.ecosystem.utilities.data.ZoneDatabase
import com.agree.ecosystem.utilities.data.reqres.ZoneDataStore
import com.agree.ecosystem.utilities.data.reqres.ZoneRepository
import com.agree.ecosystem.utilities.data.reqres.db.ZoneDb
import com.agree.ecosystem.utilities.data.reqres.db.ZoneDbImpl
import com.agree.ecosystem.utilities.data.reqres.web.zone.AgreeZoneApi
import com.agree.ecosystem.utilities.data.reqres.web.zone.AgreeZoneApiClient
import com.agree.ecosystem.utilities.domain.reqres.ZoneInteractor
import com.agree.ecosystem.utilities.domain.reqres.ZoneUsecase
import com.devbase.data.source.web.libs.createReactiveService
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Zone Module are modules that are responsible for injection of classes
 * related to Services, Repository and UseCase
 * @author: @telkomdev-abdul
 * @since: 17 Dec 2022
 */
interface ZoneModule : ViewModelModule {
    fun provideZoneModule(): Array<Module> {
        return arrayOf(
            provideZoneService(),
            provideZoneDatabase(),
            provideZoneRepository(),
            provideZoneUseCase(),
            *provideZoneViewModel()
        )
    }

    fun provideZoneService(): Module {
        return module {
            single(named(Constant.ZONE_SERVICES)) {
                createReactiveService(
                    AgreeZoneApiClient::class.java,
                    get { parametersOf(false, false) },
                    UrlManagement.getBaseUrl()
                )
            }

            single {
                AgreeZoneApi(
                    get(
                        named(
                            Constant.ZONE_SERVICES
                        )
                    )
                )
            }
        }
    }

    fun provideZoneDatabase(): Module {
        return module {
            single { createRoomDb<ZoneDatabase>(androidContext(), ZoneDatabase.getDbName()) }
            single { ZoneDbImpl(get()) } bind ZoneDb::class
        }
    }

    fun provideZoneRepository(): Module {
        return module {
            single { ZoneDataStore(get(), get()) } bind ZoneRepository::class
        }
    }

    fun provideZoneUseCase(): Module {
        return module {
            single { ZoneInteractor(get()) } bind ZoneUsecase::class
        }
    }
}
