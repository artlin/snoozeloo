package com.plcoding.snoozeloo.core.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.snoozeloo.core.data.mapper.DataMapper
import com.plcoding.snoozeloo.core.domain.db.Alarm
import com.plcoding.snoozeloo.core.domain.db.dao.AlarmsDao
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.core.ui.ViewModelAccess
import com.plcoding.snoozeloo.manager.domain.UpdateAlarmUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class LockScreenAlarmViewModel(
    private val updateAlarmUseCase: UpdateAlarmUseCase,
    private val alarmsDao: AlarmsDao,
    private val alarmEntityConverter: DataMapper<Alarm, AlarmEntity>
): ViewModel(), KoinComponent,
    ViewModelAccess<LockScreenAlarmState, LockScreenAlarmEvent> {

    override val uiState: MutableState<LockScreenAlarmState> =
        mutableStateOf(LockScreenAlarmState(alarm = null))

    fun fetchAlarm(alarmId: Int) {
        viewModelScope.launch {
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
                viewModelScope.launch {
                    uiState.value.alarm?.copy()?.let { updateAlarmUseCase.invoke(alarmEntity = it) }
                }
                uiState.value = uiState.value.copy(shouldClose = true)
            }


        }
    }
}