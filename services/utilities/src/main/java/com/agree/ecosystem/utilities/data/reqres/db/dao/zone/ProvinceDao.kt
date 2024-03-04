package com.agree.ecosystem.utilities.data.reqres.db.dao.zone

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agree.ecosystem.utilities.data.reqres.model.zone.ProvinceEntity
import io.reactivex.Single

/**
 * Province Dao is Data Access Object for [ProvinceEntity] Table
 * @author @telkomdev-abdul
 * @since 17 Dec 2022
 */
@Dao
interface ProvinceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAll(data: List<ProvinceEntity>): Single<List<Long>>

    @Query("Select * from provinceentity")
    fun getAll(): Single<List<ProvinceEntity>>
}
