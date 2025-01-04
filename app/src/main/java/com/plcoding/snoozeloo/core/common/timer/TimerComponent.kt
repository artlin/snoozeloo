package com.plcoding.snoozeloo.core.common.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TimerComponent(
    private val coroutineScope: CoroutineScope,
    private val onTick: () -> Unit
) {

    private var timerJob: Job? = null
    private var lastTickValue = 0L
    private var isRunning = false

    private val cleanupJob = coroutineScope.launch {
        coroutineScope.coroutineContext[Job]?.invokeOnCompletion {
            cancel()
        }
    }

    fun start() {
        if (timerJob?.isActive == true) return

        isRunning = true
        timerJob = coroutineScope.launch {
            while (isRunning) {
                if(lastTickValue > 0) {
                    delay(TimeUnit.MINUTES.toMillis(1))
                } else {
                    delay(getDurationToNextFullMinute())
                }
                lastTickValue++
                onTick()
            }
        }
    }

    fun stop() {
        isRunning = false
        timerJob?.cancel()
    }

    fun cancel() {
        stop()
        lastTickValue = 0
        cleanupJob.cancel()
    }

    fun getDurationToNextFullMinute(): Long {
        val currentTimeMillis = System.currentTimeMillis()
        return TimeUnit.MINUTES.toMillis(1) -
                (currentTimeMillis % TimeUnit.MINUTES.toMillis(1))
    }
}