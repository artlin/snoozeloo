package com.plcoding.snoozeloo.manager.ui.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.snoozeloo.core.data.mapper.DataMapper
import com.plcoding.snoozeloo.core.domain.db.Alarm
import com.plcoding.snoozeloo.core.domain.db.dao.AlarmsDao
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.core.domain.value.TimeValue
import com.plcoding.snoozeloo.core.ui.ViewModelAccess
import com.plcoding.snoozeloo.navigation.NavigationController
import com.plcoding.snoozeloo.navigation.route.NavigationRoute
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AlarmListViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val navigationController: NavigationController
) : ViewModel(), KoinComponent,
    ViewModelAccess<AlarmListState, AlarmListEvent> {

    private val alarmsDao: AlarmsDao by inject()
    private val entityConverter: DataMapper<Alarm, AlarmEntity> by inject()

    override var uiState: MutableState<AlarmListState> =
        mutableStateOf(AlarmListState(currentTime = TimeValue(System.currentTimeMillis())))

    init {
        viewModelScope.launch {
            alarmsDao.getAll().collectLatest { alarmDtos ->
                val alarms = entityConverter.convert(alarmDtos)
                uiState.value = uiState.value.copy(
                    list = alarms,
                    currentTime = TimeValue(System.currentTimeMillis())
                )
            }
        }
    }

    override fun onEvent(event: AlarmListEvent) {
        when (event) {
            is AlarmListEvent.AddAlarmClicked -> {
                navigationController.navigateTo(NavigationRoute.EditAlarm(alarmEntity = null))
            }

            is AlarmListEvent.AlarmCardClicked -> {
                navigationController.navigateTo(NavigationRoute.EditAlarm(alarmEntity = event.alarmEntity))
            }
        }
    }
}
