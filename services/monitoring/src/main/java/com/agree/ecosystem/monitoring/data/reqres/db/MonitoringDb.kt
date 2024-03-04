package com.agree.ecosystem.monitoring.data.reqres.db

import com.agree.ecosystem.monitoring.data.reqres.db.dao.MonitoringDao
import com.devbase.data.source.db.DbService

interface MonitoringDb : DbService {
    fun monitoringDao(): MonitoringDao
}
