package com.agree.ecosystem.users.data.watcher

import com.agree.ecosystem.core.utils.presentation.watcher.DbWatcher
import com.agree.ecosystem.users.data.UsersDatabase
import com.pluto.plugins.rooms.db.PlutoRoomsDBWatcher

object UsersWatcher : DbWatcher {
    override fun watch() {
        PlutoRoomsDBWatcher.watch(UsersDatabase.getDbName(), UsersDatabase::class.java)
    }
}
