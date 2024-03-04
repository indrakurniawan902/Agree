package com.agree.ecosystem.utils.notification

import android.app.NotificationChannel

interface AgrNotificationChannel : MainNotificationChannel {
    fun provideNotificationChannel(): List<NotificationChannel> {
        return listOf(
            provideMainNotificationChannel()
        )
    }
}
