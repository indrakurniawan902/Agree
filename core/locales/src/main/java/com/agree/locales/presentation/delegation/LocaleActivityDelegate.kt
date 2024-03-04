package com.agree.locales.presentation.delegation

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

interface LocaleActivityDelegate {
    fun initLocale(activity: AppCompatActivity, superDelegate: AppCompatDelegate)
    fun getLocaleDelegate(): AppCompatDelegate
}