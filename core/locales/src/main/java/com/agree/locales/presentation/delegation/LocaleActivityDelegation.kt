package com.agree.locales.presentation.delegation

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.ViewPumpAppCompatDelegate
import com.agree.locales.presentation.delegation.LocaleActivityDelegate
import dev.b3nedikt.restring.Restring

class LocaleActivityDelegation : LocaleActivityDelegate {
    private lateinit var activity: AppCompatActivity
    private lateinit var superDelegate: AppCompatDelegate

    private val appCompatDelegate: AppCompatDelegate by lazy {
        ViewPumpAppCompatDelegate(
            baseDelegate = superDelegate,
            baseContext = activity,
            wrapContext = Restring::wrapContext
        )
    }

    override fun initLocale(activity: AppCompatActivity, superDelegate: AppCompatDelegate) {
        this.activity = activity
        this.superDelegate = superDelegate
    }

    override fun getLocaleDelegate(): AppCompatDelegate {
        return appCompatDelegate
    }
}