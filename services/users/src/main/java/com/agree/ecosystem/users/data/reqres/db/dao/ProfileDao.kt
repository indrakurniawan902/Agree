package com.agree.ecosystem.users.data.reqres.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agree.ecosystem.users.data.reqres.model.profile.ProfileEntity
import io.reactivex.Single

@Dao
interface ProfileDao {
    @Query("Select * from profileentity where id = :id")
    fun getUsersById(id: String): Single<List<ProfileEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(users: List<ProfileEntity>): Single<List<Long>>
}
