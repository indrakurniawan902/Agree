package com.agree.ecosystem.utilities.data.reqres.db.dao.utilities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agree.ecosystem.utilities.data.reqres.model.appinfo.AppInfoEntity
import io.reactivex.Single

/**
 * AppInfoDao is Data Access Object for [AppInfoEntity] table
 * @author @telkomdev-abdul
 * @since 25 Nov 2022
 */
@Dao
interface AppInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(data: List<AppInfoEntity>): Single<List<Long>>

    @Query("select * from appinfoentity limit 1")
    fun getAppInfo(): Single<List<AppInfoEntity>>
}
