package com.agree.ui.data.watcher

import com.agree.ui.data.CoreUiDatabase
import com.pluto.plugins.rooms.db.PlutoRoomsDBWatcher

object CoreUiWatcher {
    fun watch() {
        PlutoRoomsDBWatcher.watch(CoreUiDatabase.getDbName(), CoreUiDatabase::class.java)
    }

    fun init() {
        watch()
    }
}
