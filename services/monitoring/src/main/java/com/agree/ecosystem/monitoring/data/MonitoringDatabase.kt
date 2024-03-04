package com.agree.ecosystem.monitoring.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.agree.ecosystem.monitoring.data.reqres.db.dao.MonitoringDao
import com.agree.ecosystem.monitoring.data.reqres.model.activitysop.ActivitySopEntity
import com.agree.ecosystem.monitoring.data.reqres.model.phaseactivity.PhaseActivityEntity
import com.agree.ecosystem.monitoring.data.reqres.model.phaseactivity.SubPhaseActivityEntity
import com.agree.ecosystem.monitoring.data.reqres.model.subvessel.SubVesselEntity
import com.agree.ecosystem.monitoring.data.reqres.model.subvesseldetails.DetailSubVesselEntity
import com.agree.ecosystem.monitoring.data.reqres.model.vessel.VesselEntity

@Database(
    entities = [
        VesselEntity::class,
        SubVesselEntity::class,
        ActivitySopEntity::class,
        PhaseActivityEntity::class,
        SubPhaseActivityEntity::class,
        DetailSubVesselEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MonitoringDatabase : RoomDatabase() {

    abstract fun monitoringDao(): MonitoringDao

    companion object {
        fun getDbName(): String = "monitoringDb"
    }
}
