package com.kiwicorp.timereminder

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.getSystemService
import java.time.Instant

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

        setAlarmIn30(context)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setAlarmIn30(context: Context) {
        val alarmManager: AlarmManager? = context.getSystemService()

        val timeInMillis = Instant.now().toEpochMilli() + 60000

        val notificationIntent = PendingIntent.getBroadcast(
            context,
            0,
            Intent(context, NotificationBroadcastReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager?.let {
            AlarmManagerCompat.setExactAndAllowWhileIdle(
                alarmManager,
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                notificationIntent
            )
        }
    }
}