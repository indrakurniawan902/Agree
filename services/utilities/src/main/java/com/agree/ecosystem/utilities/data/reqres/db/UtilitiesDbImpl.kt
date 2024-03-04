package com.agree.ecosystem.utilities.data.reqres.db

import com.agree.ecosystem.utilities.data.UtilitiesDatabase
import com.agree.ecosystem.utilities.data.reqres.db.dao.utilities.AppInfoDao
import com.agree.ecosystem.utilities.data.reqres.db.dao.utilities.SubSectorDao

class UtilitiesDbImpl(
    private val db: UtilitiesDatabase
) : UtilitiesDb {
    override fun appInfo(): AppInfoDao {
        return db.appInfoDao()
    }

    override fun subSector(): SubSectorDao {
        return db.subSectorDao()
    }
}
