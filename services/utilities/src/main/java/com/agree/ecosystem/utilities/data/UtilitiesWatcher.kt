package com.agree.ecosystem.utilities.data

import android.content.Context
import androidx.startup.Initializer
import com.agree.ecosystem.utilities.data.watcher.UtilitiesWatcher
import com.agree.ecosystem.utilities.data.watcher.ZoneWatcher

class UtilitiesWatcher : Initializer<Unit> {
    override fun create(context: Context) {
        UtilitiesWatcher.init()
        ZoneWatcher.init()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}
