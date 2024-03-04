package com.agree.ecosystem.users.data.reqres.db

import com.agree.ecosystem.users.data.reqres.db.dao.ProfileDao
import com.devbase.data.source.db.DbService

interface UsersDb : DbService {
    fun profileDao(): ProfileDao
}
