package com.agree.locales.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.agree.locales.data.reqres.db.dao.LocaleDao
import com.agree.locales.data.reqres.model.locale.LocaleData

@Database(
    entities = [
        LocaleData::class
    ],
    version = 1, exportSchema = false
)
abstract class LocaleDatabase : RoomDatabase() {
    abstract fun localeDao(): LocaleDao

    companion object {
        fun getDbName(): String = "localeDb"
    }
}