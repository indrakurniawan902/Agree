package com.agree.ecosystem.users.data

import android.content.Context
import androidx.startup.Initializer
import com.agree.ecosystem.users.data.watcher.UsersWatcher
import com.agree.ecosystem.utilities.data.watcher.ZoneWatcher

class UsersWatcher : Initializer<Unit> {
    override fun create(context: Context) {
        UsersWatcher.init()
        ZoneWatcher.init()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}
