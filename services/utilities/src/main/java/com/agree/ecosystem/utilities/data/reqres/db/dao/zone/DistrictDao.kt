package com.agree.ecosystem.utilities.data.reqres.db.dao.zone

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agree.ecosystem.utilities.data.reqres.model.zone.DistrictEntity
import io.reactivex.Single

/**
 * District Dao is Data Access Object for [DistrictEntity] Table
 * @author @telkomdev-abdul
 * @since 17 Dec 2022
 */
@Dao
interface DistrictDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAll(data: List<DistrictEntity>): Single<List<Long>>

    @Query("Select * from districtentity where provinceId=:id")
    fun getByIdProvince(id: String): Single<List<DistrictEntity>>
}
