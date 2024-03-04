package com.agree.ecosystem.monitoring.data.watcher

import com.agree.ecosystem.core.utils.presentation.watcher.DbWatcher
import com.agree.ecosystem.monitoring.data.MonitoringDatabase
import com.pluto.plugins.rooms.db.PlutoRoomsDBWatcher

object MonitoringWatcher : DbWatcher {
    override fun watch() {
        PlutoRoomsDBWatcher.watch(MonitoringDatabase.getDbName(), MonitoringDatabase::class.java)
    }
}
