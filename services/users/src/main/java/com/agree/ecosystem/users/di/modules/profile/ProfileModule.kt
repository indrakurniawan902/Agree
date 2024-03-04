package com.agree.ecosystem.users.di.modules.profile

import com.agree.ecosystem.core.utils.utility.Constant
import com.agree.ecosystem.core.utils.utility.UrlManagement
import com.agree.ecosystem.core.utils.utility.createRoomDb
import com.agree.ecosystem.users.data.UsersDatabase
import com.agree.ecosystem.users.data.reqres.UsersDataStore
import com.agree.ecosystem.users.data.reqres.UsersRepository
import com.agree.ecosystem.users.data.reqres.db.UsersDb
import com.agree.ecosystem.users.data.reqres.db.UsersDbImpl
import com.agree.ecosystem.users.data.reqres.web.AgreeUsersApi
import com.agree.ecosystem.users.data.reqres.web.AgreeUsersApiClient
import com.agree.ecosystem.users.domain.reqres.UsersInteractor
import com.agree.ecosystem.users.domain.reqres.UsersUsecase
import com.devbase.data.source.web.libs.createReactiveService
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Profile Module are modules that are responsible for injection of classes
 * related to Services, Repository and UseCase
 * @author: @telkomdev-abdul
 * @since: 20 Dec 2022
 */
interface ProfileModule : ViewModelModule {
    fun provideProfileModule(): Array<Module> {
        return arrayOf(
            provideProfileService(),
            provideProfileRepository(),
            provideProfileUseCase(),
            provideProfileDatabase(),
            *provideProfileViewModel()
        )
    }

    fun provideProfileService(): Module {
        return module {
            single(named(Constant.USERS_SERVICES)) {
                createReactiveService(
                    AgreeUsersApiClient::class.java,
                    get { parametersOf(false, true) },
                    UrlManagement.getBaseUrl()
                )
            }

            single { AgreeUsersApi(get(named(Constant.USERS_SERVICES))) }
        }
    }

    fun provideProfileDatabase(): Module {
        return module {
            single { createRoomDb<UsersDatabase>(androidContext(), UsersDatabase.getDbName()) }
            single { UsersDbImpl(get()) } bind UsersDb::class
        }
    }

    fun provideProfileRepository(): Module {
        return module {
            single {
                UsersDataStore(get(), get())
            } bind UsersRepository::class
        }
    }

    fun provideProfileUseCase(): Module {
        return module {
            single {
                UsersInteractor(get())
            } bind UsersUsecase::class
        }
    }
}
