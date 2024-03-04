package com.agree.ecosystem.utilities.data.watcher

import com.agree.ecosystem.core.utils.presentation.watcher.DbWatcher
import com.agree.ecosystem.utilities.data.ZoneDatabase
import com.pluto.plugins.rooms.db.PlutoRoomsDBWatcher

object ZoneWatcher : DbWatcher {
    override fun watch() {
        PlutoRoomsDBWatcher.watch(ZoneDatabase.getDbName(), ZoneDatabase::class.java)
    }
}
