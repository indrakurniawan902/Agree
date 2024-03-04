package com.agree.ecosystem.utilities.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.agree.ecosystem.utilities.data.reqres.db.dao.utilities.AppInfoDao
import com.agree.ecosystem.utilities.data.reqres.db.dao.utilities.SubSectorDao
import com.agree.ecosystem.utilities.data.reqres.model.appinfo.AppInfoEntity
import com.agree.ecosystem.utilities.data.reqres.model.subsector.SubSectorEntity

@Database(
    entities = [
        AppInfoEntity::class,
        SubSectorEntity::class
    ],
    version = 2, exportSchema = false
)
abstract class UtilitiesDatabase : RoomDatabase() {

    abstract fun appInfoDao(): AppInfoDao
    abstract fun subSectorDao(): SubSectorDao

    companion object {
        fun getDbName(): String = "utilitiesDb"
    }
}
