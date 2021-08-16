package com.kiwicorp.timereminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.getSystemService
import com.google.android.material.switchmaterial.SwitchMaterial
import java.time.Instant
import java.time.ZonedDateTime

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val switch: SwitchMaterial = findViewById(R.id._switch)

        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) startNotis()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startNotis() {
        val alarmManager: AlarmManager? = getSystemService()

        val timeInMillis = Instant.now().toEpochMilli()

        val notificationIntent = PendingIntent.getBroadcast(
            this,
            0,
            Intent(this,NotificationBroadcastReceiver::class.java),
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