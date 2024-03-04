package com.agree.ecosystem.users.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.agree.ecosystem.users.data.reqres.db.dao.ProfileDao
import com.agree.ecosystem.users.data.reqres.model.profile.ProfileEntity

@Database(
    entities = [
        ProfileEntity::class
    ],
    version = 1, exportSchema = false
)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun profileDao(): ProfileDao

    companion object {
        fun getDbName(): String = "usersDb"
    }
}
