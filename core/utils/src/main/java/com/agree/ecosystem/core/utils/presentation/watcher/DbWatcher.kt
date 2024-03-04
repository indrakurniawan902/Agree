package com.agree.ecosystem.core.utils.presentation.watcher

import com.agree.ecosystem.core.utils.utility.AppConfig

interface DbWatcher {
    fun watch()

    fun init() {
        if (!AppConfig.isDebug) {
            return
        }
        watch()
    }
}
