package com.plcoding.snoozeloo.core.common.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                delay(1000)
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
}