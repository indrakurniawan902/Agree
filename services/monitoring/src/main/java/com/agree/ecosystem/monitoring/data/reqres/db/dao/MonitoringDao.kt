package com.agree.ecosystem.monitoring.data.reqres.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.sqlite.db.SupportSQLiteQuery
import com.agree.ecosystem.monitoring.data.reqres.model.activitysop.ActivitySopEntity
import com.agree.ecosystem.monitoring.data.reqres.model.phaseactivity.PhaseActivityEntity
import com.agree.ecosystem.monitoring.data.reqres.model.phaseactivity.PhaseAndSubPhaseActivity
import com.agree.ecosystem.monitoring.data.reqres.model.phaseactivity.SubPhaseActivityEntity
import com.agree.ecosystem.monitoring.data.reqres.model.subvessel.SubVesselEntity
import com.agree.ecosystem.monitoring.data.reqres.model.subvesseldetails.DetailSubVesselEntity
import com.agree.ecosystem.monitoring.data.reqres.model.vessel.VesselEntity
import io.reactivex.Single

@Dao
interface MonitoringDao {

    @RawQuery
    fun getVessel(query: SupportSQLiteQuery): Single<List<VesselEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllVessel(vessel: List<VesselEntity>): Single<List<Long>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllSubVessel(data: List<SubVesselEntity>): Single<List<Long>>

    @Query("Select * from subvesselentity")
    fun getAllSubVessel(): Single<List<SubVesselEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllActivitySop(data: List<ActivitySopEntity>): Single<List<Long>>

    @Query(
        "Select * from activitysopentity where " +
            "subVesselId = :subVesselId " +
            "and date LIKE :date " +
            "and isAdditional = :isAdditional"
    )
    fun getAllActivitySop(
        subVesselId: String,
        date: String,
        isAdditional: Int
    ): Single<List<ActivitySopEntity>>

    @RawQuery
    fun getSubVessel(query: SupportSQLiteQuery): Single<List<SubVesselEntity>>

    @Transaction
    @Query("SELECT * FROM phaseactivityentity WHERE subVesselId = :subVesselId")
    fun getPhaseActivity(subVesselId: String): Single<List<PhaseAndSubPhaseActivity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllPhaseActivity(data: List<PhaseActivityEntity>): Single<List<Long>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllSubPhaseActivity(data: List<SubPhaseActivityEntity>): Single<List<Long>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllDetailSubVessel(data: List<DetailSubVesselEntity>): Single<List<Long>>

    @Query("Select * from detailsubvesselentity")
    fun getAllDetailSubVessel(): Single<DetailSubVesselEntity>
}
