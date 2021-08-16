package com.kiwicorp.timereminder

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import androidx.annotation.RequiresApi
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.getSystemService
import com.google.android.material.switchmaterial.SwitchMaterial
import java.time.Instant

class MainActivity : AppCompatActivity() {
    companion object {
        private const val PREFS_SWITCH = "prefs_switch"
    }

    private lateinit var prefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor
    private var alarmManager: AlarmManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        prefsEditor = prefs.edit()
        alarmManager = getSystemService()

        val switch: SwitchMaterial = findViewById(R.id._switch)
        // load switch state from cache
        switch.isChecked = prefs.getBoolean(PREFS_SWITCH, false)

        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            // cache switch state
            prefsEditor.putBoolean(PREFS_SWITCH, isChecked)
            prefsEditor.apply()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (isChecked)  {
                    startNotis()
                } else {
                    stopNotis()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startNotis() {
        val timeInMillis = Instant.now().toEpochMilli()

        val notificationIntent = PendingIntent.getBroadcast(
            this,
            0,
            Intent(this, NotificationBroadcastReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager?.let {
            AlarmManagerCompat.setExactAndAllowWhileIdle(
                alarmManager!!,
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                notificationIntent
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun stopNotis() {
        val notificationManager: NotificationManager = getSystemService()
            ?: throw Exception("Notification Manager not found.")

        val notificationIntent = PendingIntent.getBroadcast(
            this,
            0,
            Intent(this, NotificationBroadcastReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        notificationIntent?.let {
            alarmManager?.cancel(notificationIntent)
        }

        notificationManager.cancel(0)
    }
}