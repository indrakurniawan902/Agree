package com.agree.ecosystem.monitoring.data

import android.content.Context
import androidx.startup.Initializer
import com.agree.ecosystem.monitoring.data.watcher.MonitoringWatcher

class MonitoringWatcher : Initializer<Unit> {
    override fun create(context: Context) {
        MonitoringWatcher.init()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}
