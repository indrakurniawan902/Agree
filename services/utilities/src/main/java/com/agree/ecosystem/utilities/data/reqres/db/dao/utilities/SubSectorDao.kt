package com.agree.ecosystem.utilities.data.reqres.db.dao.utilities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agree.ecosystem.utilities.data.reqres.model.subsector.SubSectorEntity
import io.reactivex.Single

/**
 * SubSectorDao is Data Access Object for [SubSectorEntity] table
 * @author @telkomdev-abdul
 * @since 31 May 2023
 */
@Dao
interface SubSectorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(data: List<SubSectorEntity>): Single<List<Long>>

    @Query("Select * from subsectorentity")
    fun getAllSubSectors(): Single<List<SubSectorEntity>>
}
