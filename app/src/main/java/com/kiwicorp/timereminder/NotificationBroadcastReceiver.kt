package com.kiwicorp.timereminder

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService

class NotificationBroadcastReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager: NotificationManager = context.getSystemService()
            ?: throw Exception("Notification Manager not found.")

        val notification = Notification.Builder(context, CHANNEL_ID_REMINDER)
            .setContentTitle("30 MINUTES HAS PASSED")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setShowWhen(true)
            .setAutoCancel(true).build()

        notificationManager.notify(0, notification)
    }
}