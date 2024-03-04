package com.agree.ecosystem.users.data.reqres.db

import com.agree.ecosystem.users.data.UsersDatabase
import com.agree.ecosystem.users.data.reqres.db.dao.ProfileDao

class UsersDbImpl(
    private val db: UsersDatabase
) : UsersDb {
    override fun profileDao(): ProfileDao {
        return db.profileDao()
    }
}
