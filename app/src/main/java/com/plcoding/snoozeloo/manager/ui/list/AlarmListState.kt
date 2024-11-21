package com.plcoding.snoozeloo.manager.ui.list

import com.plcoding.snoozeloo.manager.domain.AlarmEntity
import com.plcoding.snoozeloo.manager.domain.TimeValue

data class AlarmListState(val list: List<AlarmEntity> = emptyList(), val currentTime: TimeValue)


