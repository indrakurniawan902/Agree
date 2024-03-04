package com.agree.ecosystem.utilities.data.reqres.db.dao.zone

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agree.ecosystem.utilities.data.reqres.model.zone.VillageEntity
import io.reactivex.Single

@Dao
interface VillageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAll(data: List<VillageEntity>): Single<List<Long>>

    @Query("Select * from villageentity where subDistrictId=:id")
    fun getAllBySubDistrictId(id: String): Single<List<VillageEntity>>
}
