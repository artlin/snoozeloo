package com.plcoding.snoozeloo.alarm_volume.presentation

data class AlarmVolumeSubState(val currentVolume : Float){
    fun changeVolume(newVolume : Float) : AlarmVolumeSubState{
        return copy(currentVolume = newVolume)
    }

    companion object{
        fun getDefault() : AlarmVolumeSubState{
            return AlarmVolumeSubState(0.5f)
        }
    }
}
