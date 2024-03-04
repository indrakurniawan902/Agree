package com.agree.ecosystem.utilities.data.reqres.db

import com.agree.ecosystem.utilities.data.reqres.db.dao.utilities.AppInfoDao
import com.agree.ecosystem.utilities.data.reqres.db.dao.utilities.SubSectorDao
import com.devbase.data.source.db.DbService

interface UtilitiesDb : DbService {
    fun appInfo(): AppInfoDao

    fun subSector(): SubSectorDao
}
