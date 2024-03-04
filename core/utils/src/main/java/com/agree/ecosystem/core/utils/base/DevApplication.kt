package com.agree.ecosystem.core.utils.base

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.annotation.StringRes
import androidx.multidex.MultiDexApplication
import com.devbase.utils.AppContext
import org.acra.ReportField
import org.acra.config.toast
import org.acra.data.StringFormat
import org.acra.ktx.initAcra
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

/**
 * Application class for maintaining global state
 *
 * @author Jimly A.
 * @since 31-May-20.
 */
abstract class DevApplication : MultiDexApplication() {

    /**
     * Define series of action when application initialized
     */
    abstract fun initApplication()

    /**
     * Method to declare list of Koin Modules within apps
     * @return list of koin modules
     */
    abstract fun defineKoinModules(): List<Module>

    /**
     * Method to define notification channels
     * @return list of notification channels
     */
    abstract fun defineNotificationChannel(): List<NotificationChannel>

    /**
     * Method to define BuildConfig for the application
     * Sample:

     ```kotlin
     fun buildConfigClass() = BuildConfig::class.java
     ```

     * note: make sure to import the right BuildConfig for the app module
     *
     * @return BuildConfig Class for the Application
     */
    abstract fun buildConfigClass(): Class<*>

    /**
     * Method to define error String for the toast when the app crashed
     * @return String resource id to display
     */
    @StringRes
    abstract fun errorMessageResourceId(): Int

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        initAcra {
            withBuildConfigClass(buildConfigClass())
            withReportFormat(StringFormat.JSON)
            withReportContent(
                ReportField.APP_VERSION_CODE,
                ReportField.APP_VERSION_NAME,
                ReportField.PHONE_MODEL,
                ReportField.ANDROID_VERSION,
                ReportField.STACK_TRACE,
                ReportField.LOGCAT
            )
            toast {
                withResText(errorMessageResourceId())
                withEnabled(true)
                withLength(1)
            }
        }
    }

    /**
     * Method to declare action on global state
     * For now, it's used to :
     * 1. start Koin Injection
     * 2. Timber Plant Log
     * 3. Setting App Context
     */
    override fun onCreate() {
        super.onCreate()
        AppContext.setBaseContext(applicationContext)
        startKoin {
            androidContext(applicationContext)
            modules(defineKoinModules())
            androidLogger(Level.INFO)
        }
        createNotificationChannel()
        initApplication()
    }

    /**
     * Method to create notification from channels
     */
    private fun createNotificationChannel() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        defineNotificationChannel().forEach {
            if (VERSION.SDK_INT >= VERSION_CODES.O) {
                notificationManager.createNotificationChannel(
                    it.apply {
                        enableLights(true)
                        setShowBadge(true)
                        setSound(
                            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),
                            null
                        )
                        lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                    }
                )
            }
        }
    }
}
