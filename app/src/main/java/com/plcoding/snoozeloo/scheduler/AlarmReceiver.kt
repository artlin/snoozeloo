package com.plcoding.snoozeloo.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.plcoding.snoozeloo.service.AlarmService

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmId = intent?.getIntExtra("ALARM_ID", -1)
        if (alarmId == null || alarmId == -1) {
            return
        }

        println("Alarm triggered: $alarmId")
        context?.let {
            println("Alarm triggered: context not null")


            Intent(it, AlarmService::class.java).also { serviceIntent ->
                serviceIntent.action = AlarmService.Actions.START_FOREGROUND_SERVICE.toString()
                serviceIntent.putExtra("ALARM_ID", alarmId)
                ContextCompat.startForegroundService(it, serviceIntent)
            }
        }
    }

}