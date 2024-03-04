package com.agree.ecosystem.utilities.data.reqres.db.dao.zone

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agree.ecosystem.utilities.data.reqres.model.zone.SubDistrictEntity
import io.reactivex.Single

@Dao
interface SubDistrictDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAll(data: List<SubDistrictEntity>): Single<List<Long>>

    @Query("Select * from subdistrictentity where districtId = :id")
    fun getByIdDistrict(id: String): Single<List<SubDistrictEntity>>
}
