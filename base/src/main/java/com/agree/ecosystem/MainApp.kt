package com.agree.ecosystem

import android.app.NotificationChannel
import android.content.Context
import android.os.Build
import androidx.startup.AppInitializer
import com.agree.ecosystem.agreepedia.di.AgreepediaModule
import com.agree.ecosystem.auth.di.AuthModule
import com.agree.ecosystem.common.di.CommonModule
import com.agree.ecosystem.core.analytics.di.CoreAnalyticsModule
import com.agree.ecosystem.core.utils.base.AgrApplication
import com.agree.ecosystem.core.utils.di.CoreUtilsModule
import com.agree.ecosystem.core.utils.utility.AppConfig
import com.agree.ecosystem.di.AppModule
import com.agree.ecosystem.finances.di.FinancesModule
import com.agree.ecosystem.monitoring.data.MonitoringWatcher
import com.agree.ecosystem.monitoring.di.MonitoringModule
import com.agree.ecosystem.partnership.di.PartnershipModule
import com.agree.ecosystem.smartfarming.di.SmartfarmingModule
import com.agree.ecosystem.splash.di.SplashModule
import com.agree.ecosystem.users.data.UsersWatcher
import com.agree.ecosystem.users.di.UsersModule
import com.agree.ecosystem.utilities.data.UtilitiesWatcher
import com.agree.ecosystem.utilities.di.UtilitiesModule
import com.agree.ecosystem.utils.notification.AgrNotificationChannel
import com.agree.locales.LanguageHelper
import com.agree.locales.data.LocalesWatchers
import com.agree.locales.di.CoreLocalesModule
import com.agree.ui.data.CoreUiWatcher
import com.agree.ui.di.CoreUiModule
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.pluto.Pluto
import com.pluto.plugins.exceptions.PlutoExceptionsPlugin
import com.pluto.plugins.layoutinspector.PlutoLayoutInspectorPlugin
import com.pluto.plugins.preferences.PlutoSharePreferencesPlugin
import com.pluto.plugins.rooms.db.PlutoRoomsDatabasePlugin
import org.koin.core.module.Module

class MainApp :
    AgrApplication(),
    AgrNotificationChannel {

    override fun initApplication() {
        super.initApplication()
        /**
         * Add Pluto Crash Tracker for Build Type Debug
         */
        Pluto.Installer(this)
            .addPlugin(PlutoExceptionsPlugin("AgrException"))
            .addPlugin(PlutoRoomsDatabasePlugin("AgrDatabase"))
            .addPlugin(PlutoSharePreferencesPlugin("AgrPreference"))
            .addPlugin(PlutoLayoutInspectorPlugin("AgrLayoutInspector"))
            .install()

        /**
         * Add Pluto Database Watcher
         */
        AppInitializer.getInstance(this).apply {
            initializeComponent(LocalesWatchers::class.java)
            initializeComponent(UtilitiesWatcher::class.java)
            initializeComponent(UsersWatcher::class.java)
            initializeComponent(MonitoringWatcher::class.java)
            initializeComponent(CoreUiWatcher::class.java)
        }

        /**
         * Add Locale Support
         */
        LanguageHelper.initLocaleSupport(this)

        Firebase.messaging.subscribeToTopic("all")
    }

    /**
     * Setup for Dynamic Feature Module
     */
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }

    override fun buildConfigClass(): Class<*> {
        return BuildConfig::class.java
    }

    /**
     * Inject some koin modules
     */
    override fun defineKoinModules(): List<Module> {
        val initializer = AppInitializer.getInstance(this)
        return listOf(
            *initializer.initializeComponent(AppModule::class.java),
            *initializer.initializeComponent(AgreepediaModule::class.java),
            *initializer.initializeComponent(PartnershipModule::class.java),
            *initializer.initializeComponent(MonitoringModule::class.java),
            *initializer.initializeComponent(UtilitiesModule::class.java),
            *initializer.initializeComponent(UsersModule::class.java),
            *initializer.initializeComponent(FinancesModule::class.java),
            *initializer.initializeComponent(CoreAnalyticsModule::class.java),
            *initializer.initializeComponent(CoreLocalesModule::class.java),
            *initializer.initializeComponent(CoreUiModule::class.java),
            *initializer.initializeComponent(CoreUtilsModule::class.java),
            *initializer.initializeComponent(SplashModule::class.java),
            *initializer.initializeComponent(AuthModule::class.java),
            *initializer.initializeComponent(CommonModule::class.java),
            *initializer.initializeComponent(SmartfarmingModule::class.java)
        )
    }

    override fun defineNotificationChannel(): List<NotificationChannel> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            provideNotificationChannel()
        } else {
            emptyList()
        }
    }

    /**
     * Assign Value into AppConfig from BuildConfig
     */
    override val initConfig: (AppConfig) -> Unit = {
        it.isDebug = BuildConfig.DEBUG
        it.appId = BuildConfig.APPLICATION_ID
        it.buildType = BuildConfig.BUILD_TYPE
        it.versionName = BuildConfig.VERSION_NAME
        it.env = BuildConfig.FLAVOR
        it.isUiKitTest = BuildConfig.UIKIT_TEST
    }
}
