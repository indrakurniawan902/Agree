package com.agree.locales.presentation.activitycallback

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.locales.LanguageHelper
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.Utils
import dev.b3nedikt.app_locale.AppLocale
import dev.b3nedikt.app_locale.LocaleChangedListener
import dev.b3nedikt.reword.Reword
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Activity Callback for Locale Framework, this will trigger
 * when locale configuration changed
 */
class LocaleActivityCallback :
    Utils.ActivityLifecycleCallbacks(), KoinComponent, LocaleChangedListener {

    private var alreadyReword = mutableListOf<String>()
    private val pref: AgrPrefUsecase by inject()

    override fun onActivityCreated(activity: Activity) {
        super.onActivityCreated(activity)
        AppLocale.addLocaleChangedListener(this)
    }

    override fun onActivityResumed(activity: Activity) {
        super.onActivityResumed(activity)
        reword(activity)
    }

    override fun onLocaleChanged() {
        val tag = AppLocale.currentLocale.toLanguageTag()
        pref.currentLocale = tag
        alreadyReword.clear()
        reword()
        LanguageHelper.injectLanguage(tag)
    }

    private fun reword(activity: Activity? = null) {
        val topActivity = activity ?: ActivityUtils.getTopActivity()
        if (alreadyReword.contains(topActivity.javaClass.simpleName)) return
        runCatching {
            topActivity.findViewById<View>(android.R.id.content) as ViewGroup
        }.getOrNull()?.also {
            Reword.reword(it)
        }
        alreadyReword.add(topActivity.javaClass.simpleName)
    }
}