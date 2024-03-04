package com.agree.ecosystem.utils.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.agree.ecosystem.core.utils.utility.Constant

interface MainNotificationChannel {
    @RequiresApi(Build.VERSION_CODES.O)
    fun provideMainNotificationChannel(): NotificationChannel {
        return NotificationChannel(
            Constant.CHANNEL_ID_MAIN,
            Constant.CHANNEL_NAME_MAIN,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = Constant.CHANNEL_DESCRIPTION_MAIN
            enableLights(true)
            enableVibration(true)
        }
    }
}
