package com.agree.ecosystem.utilities.data.watcher

import com.agree.ecosystem.core.utils.presentation.watcher.DbWatcher
import com.agree.ecosystem.utilities.data.UtilitiesDatabase
import com.pluto.plugins.rooms.db.PlutoRoomsDBWatcher

object UtilitiesWatcher : DbWatcher {
    override fun watch() {
        PlutoRoomsDBWatcher.watch(UtilitiesDatabase.getDbName(), UtilitiesDatabase::class.java)
    }
}
