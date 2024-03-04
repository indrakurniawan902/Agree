package com.agree.ecosystem.monitoring.data.reqres.db

import com.agree.ecosystem.monitoring.data.MonitoringDatabase
import com.agree.ecosystem.monitoring.data.reqres.db.dao.MonitoringDao

class MonitoringDbImpl(
    private val db: MonitoringDatabase
) : MonitoringDb {
    override fun monitoringDao(): MonitoringDao {
        return db.monitoringDao()
    }
}
