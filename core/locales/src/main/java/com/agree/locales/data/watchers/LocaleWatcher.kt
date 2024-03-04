package com.agree.locales.data.watchers

import com.agree.ecosystem.core.utils.presentation.watcher.DbWatcher
import com.agree.locales.data.LocaleDatabase
import com.pluto.plugins.rooms.db.PlutoRoomsDBWatcher

object LocaleWatcher: DbWatcher {
    override fun watch() {
        PlutoRoomsDBWatcher.watch(LocaleDatabase.getDbName(), LocaleDatabase::class.java)
    }
}