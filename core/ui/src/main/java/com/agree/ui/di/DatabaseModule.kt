package com.agree.ui.di

import androidx.room.Room
import com.agree.ui.data.CoreUiDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

interface DatabaseModule {
    fun provideDatabase(): Module {
        return module {
            single {
                Room.databaseBuilder(
                    androidContext(),
                    CoreUiDatabase::class.java,
                    CoreUiDatabase.getDbName()
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }
}
