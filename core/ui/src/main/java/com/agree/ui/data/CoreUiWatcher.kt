package com.agree.ui.data

import android.content.Context
import androidx.startup.Initializer
import com.agree.ui.data.watcher.CoreUiWatcher

class CoreUiWatcher : Initializer<Unit> {
    override fun create(context: Context) {
        CoreUiWatcher.init()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}
