package com.plcoding.snoozeloo.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.plcoding.snoozeloo.service.AlarmService

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmId = intent?.getIntExtra(ALARM_ID, -1)
        if (alarmId == null || alarmId == -1) {
            return
        }

        println("$TAG Alarm triggered: $alarmId")
        context?.let {
            println("$TAG Alarm triggered: context not null")

            when (intent.action) {
                ACTION_DISMISS -> {
                    println("$TAG Dismiss action for alarm $alarmId")
                    startAlarmService(context, alarmId, AlarmService.Actions.STOP_FOREGROUND_SERVICE_DISMISS)
                }
                ACTION_SNOOZE -> {
                    println("$TAG Snooze action for alarm $alarmId")
                    startAlarmService(context, alarmId, AlarmService.Actions.STOP_FOREGROUND_SERVICE_SNOOZE)
                }
                else -> {
                    println("$TAG Start action for alarm $alarmId")
                    startAlarmService(context, alarmId, AlarmService.Actions.START_FOREGROUND_SERVICE)
                }
            }
        }
    }

    private fun startAlarmService(context: Context, alarmId: Int, action: AlarmService.Actions) {
        Intent(context, AlarmService::class.java).also { serviceIntent ->
            serviceIntent.action = action.toString()
            serviceIntent.putExtra(ALARM_ID, alarmId)
            ContextCompat.startForegroundService(context, serviceIntent)
        }
    }

    companion object {
        const val TAG = "AlarmReceiver"
        const val ALARM_ID = "ALARM_ID"
        const val ALARM_FLAG = "ALARM_FLAG"
        const val ACTION_DISMISS = "ACTION_DISMISS"
        const val ACTION_SNOOZE = "ACTION_SNOOZE"
    }

    enum class AlarmDismissType {
        DISMISS, SNOOZE, NONE
    }
}