package com.plcoding.snoozeloo.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.plcoding.snoozeloo.service.AlarmService

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return

        println("Alarm triggered: $message")
        context?.let {
            println("Alarm triggered: context not null")
            Intent(it, AlarmService::class.java).also { serviceIntent ->
                serviceIntent.action = AlarmService.Actions.START_FOREGROUND_SERVICE.toString()
                ContextCompat.startForegroundService(it, serviceIntent)
            }
        }
    }

}