package com.agree.locales.data.reqres.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agree.locales.data.reqres.model.locale.LocaleData
import io.reactivex.Single

/**
 * AppInfoDao is Data Access Object for [LocaleData] table
 * @author @telkomdev-abdul
 * @since 21 Dec 2022
 */
@Dao
interface LocaleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(data: List<LocaleData>): Single<List<Long>>

    @Query("Select * From resourceString")
    fun getAll(): Single<List<LocaleData>>
}