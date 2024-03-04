package com.agree.ecosystem.utilities.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.agree.ecosystem.utilities.data.reqres.db.dao.zone.DistrictDao
import com.agree.ecosystem.utilities.data.reqres.db.dao.zone.ProvinceDao
import com.agree.ecosystem.utilities.data.reqres.db.dao.zone.SubDistrictDao
import com.agree.ecosystem.utilities.data.reqres.db.dao.zone.VillageDao
import com.agree.ecosystem.utilities.data.reqres.model.zone.DistrictEntity
import com.agree.ecosystem.utilities.data.reqres.model.zone.ProvinceEntity
import com.agree.ecosystem.utilities.data.reqres.model.zone.SubDistrictEntity
import com.agree.ecosystem.utilities.data.reqres.model.zone.VillageEntity

@Database(
    entities = [
        ProvinceEntity::class,
        DistrictEntity::class,
        SubDistrictEntity::class,
        VillageEntity::class
    ],
    version = 1, exportSchema = false
)
abstract class ZoneDatabase : RoomDatabase() {

    abstract fun province(): ProvinceDao
    abstract fun district(): DistrictDao
    abstract fun subDistrict(): SubDistrictDao
    abstract fun village(): VillageDao

    companion object {
        fun getDbName(): String = "zoneDb"
    }
}
