package com.plcoding.snoozeloo.core.domain

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.snoozeloo.core.data.mapper.DataMapper
import com.plcoding.snoozeloo.core.domain.db.Alarm
import com.plcoding.snoozeloo.core.domain.db.dao.AlarmsDao
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.core.ui.ViewModelAccess
import com.plcoding.snoozeloo.scheduler.AlarmReceiver.Companion.ALARM_ID
import com.plcoding.snoozeloo.service.AlarmService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class LockScreenAlarmViewModel(
    private val alarmsDao: AlarmsDao,
    private val alarmEntityConverter: DataMapper<Alarm, AlarmEntity>,
    private val context: Context,
) : ViewModel(), KoinComponent,
    ViewModelAccess<LockScreenAlarmState, LockScreenAlarmEvent> {

    override val uiState: MutableState<LockScreenAlarmState> =
        mutableStateOf(LockScreenAlarmState(alarm = null))

    fun fetchAlarm(alarmId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            alarmsDao.observeAlarmById(alarmId).collectLatest { alarmDto ->
                val alarm = alarmEntityConverter.convert(alarmDto)
                uiState.value = uiState.value.copy(alarm = alarm)
            }
        }
    }

    override fun onEvent(event: LockScreenAlarmEvent) {
        when (event) {
            is LockScreenAlarmEvent.AlarmSet -> {
                fetchAlarm(event.alarmId)
            }

            is LockScreenAlarmEvent.CloseClicked -> {
                startAlarmService(uiState.value.alarm?.uid ?: -1, AlarmService.Actions.STOP_FOREGROUND_SERVICE_DISMISS)
                uiState.value = uiState.value.copy(shouldClose = true)
            }

            is LockScreenAlarmEvent.SnoozeClicked -> {
                startAlarmService(uiState.value.alarm?.uid ?: -1, AlarmService.Actions.STOP_FOREGROUND_SERVICE_SNOOZE)
                uiState.value = uiState.value.copy(shouldClose = true)
            }
        }
    }

    private fun startAlarmService(alarmId: Int, action: AlarmService.Actions) {
        Intent(context, AlarmService::class.java).also { serviceIntent ->
            serviceIntent.action = action.toString()
            serviceIntent.putExtra(ALARM_ID, alarmId)
            ContextCompat.startForegroundService(context, serviceIntent)
        }
    }
}