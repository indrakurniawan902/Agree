package com.agree.ecosystem.utils.service

import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.agree.ecosystem.common.presentation.base.activity.CommonActivity
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.Constant
import com.agree.ecosystem.utils.notification.AgrNotificationChannel
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.android.ext.android.inject

class FcmService : FirebaseMessagingService(), AgrNotificationChannel {
    private val prefs: AgrPrefUsecase by inject()
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) prefs.fcmToken = it.result
            else Log.e("FcmService", "FcmService: ${it.exception?.message}", it.exception)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        showNotification(message)
    }

    private fun showNotification(message: RemoteMessage) {
        val notificationTitle: String = message.notification?.title.toString()
        val notificationContent = message.notification?.body.toString()
        val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBundle = Bundle()
        val vibrationArray = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

        val notificationDestination: Int = when {
            message.data.containsKey("inboxId") -> {
                notificationBundle.putString("inboxId", message.data["inboxId"])
                com.agree.ecosystem.common.R.id.detailNotificationFragment
            }
            else -> {
                com.agree.ecosystem.common.R.id.homeFragment
            }
        }

        val deepLinkIntent = NavDeepLinkBuilder(applicationContext)
            .setComponentName(CommonActivity::class.java)
            .setGraph(com.agree.ecosystem.common.R.navigation.nav_menu)
            .setDestination(notificationDestination)
            .setArguments(notificationBundle)
            .createPendingIntent()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            checkNotificationChannel()
        }

        val notification =
            NotificationCompat.Builder(applicationContext, Constant.CHANNEL_NAME_MAIN)
                .setSmallIcon(com.agree.ecosystem.R.drawable.ic_logo_primary)
                .setContentTitle(notificationTitle)
                .setContentText(notificationContent)
                .setContentIntent(deepLinkIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(notificationSound)
                .setVibrate(vibrationArray)
                .setAutoCancel(true)
                .setChannelId(Constant.CHANNEL_ID_MAIN)

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(Constant.NOTIFICATION_ID_MAIN, notification.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkNotificationChannel() {
        val notificationChannel = provideMainNotificationChannel()

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
    }
}
