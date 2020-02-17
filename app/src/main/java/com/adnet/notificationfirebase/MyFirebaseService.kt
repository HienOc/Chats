@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.adnet.notificationfirebase

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import com.adnet.notificationfirebase.Utils.Constant
import com.adnet.notificationfirebase.activity.LoginActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        remoteMessage?.notification?.body?.let { setNotification(it) }
    }

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
    }

    private fun sendRegistrationToServer(token: String) { // TODO: Implement this method to send token to your app server.
    }


    private fun setNotification(messageBody: String){
        val intentFlag = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_SINGLE_TOP or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_NEW_TASK
        val intentNotification = LoginActivity.getIntent(this).apply {
            intentFlag
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intentNotification,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(
                Constant.CHANNEL_ID,
                Constant.CHANNEL_NAME,
                importance
            )
            notificationChannel.description = Constant.CHANNEL_DESCRIPTION
            getSystemService(
                NotificationManager::class.java
            ).createNotificationChannel(notificationChannel)
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, getNotification(pendingIntent))
    }
    private fun getNotification(
        pendingIntent: PendingIntent
    ): Notification = NotificationCompat.Builder(
        this, Constant.CHANNEL_ID
    )
        .setSmallIcon(R.drawable.ic_btn_speak_now)
        .setContentTitle("Haizzzz")
        .setContentText("Haizzzz")
        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
        .setDefaults(Notification.DEFAULT_SOUND)
        .setAutoCancel(true)
        .setPriority(6)
        .setContentIntent(pendingIntent)
        .build()

}