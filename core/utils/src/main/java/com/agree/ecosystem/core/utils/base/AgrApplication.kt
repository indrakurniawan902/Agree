package com.agree.ecosystem.core.utils.base

import androidx.core.os.bundleOf
import com.agree.ecosystem.core.analytics.AgrAnalytics
import com.agree.ecosystem.core.utils.R
import com.agree.ecosystem.core.utils.utility.AppConfig
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.toString
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.util.*

abstract class AgrApplication : DevApplication() {

    open val analytics by inject<AgrAnalytics>()

    override fun errorMessageResourceId(): Int {
        return R.string.error_message_app
    }

    override fun initApplication() {
        initConfig.invoke(AppConfig)

        if (AppConfig.isDebug) {
            Timber.plant(Timber.DebugTree())
        }

        analytics.logEvent(
            FirebaseAnalytics.Event.APP_OPEN,
            bundleOf(
                Pair("date", Date().toString(ConverterDate.SIMPLE_DATE_TIME))
            )
        )
    }

    abstract val initConfig: (AppConfig) -> Unit
}
