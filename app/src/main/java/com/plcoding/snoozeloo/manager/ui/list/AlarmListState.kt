package com.plcoding.snoozeloo.manager.ui.list

import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.core.domain.value.TimeValue

data class AlarmListState(val list: List<AlarmEntity> = emptyList(), val currentTime: TimeValue)


