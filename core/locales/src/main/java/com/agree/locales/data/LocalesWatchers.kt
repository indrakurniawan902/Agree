package com.agree.locales.data

import android.content.Context
import androidx.startup.Initializer
import com.agree.locales.data.watchers.LocaleWatcher
import org.koin.core.module.Module


class LocalesWatchers : Initializer<Unit> {
    override fun create(context: Context) {
        LocaleWatcher.init()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}