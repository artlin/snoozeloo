package com.plcoding.snoozeloo.alarm_selection.presentation

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.plcoding.snoozeloo.alarm_selection.domain.GetSystemRingtonesUseCase
import com.plcoding.snoozeloo.core.domain.entity.RingtoneEntity

class RingtonesManager(
    private val context: Context,
    private val getSystemRingtonesUseCase: GetSystemRingtonesUseCase
) {

    private var currentRingtone: Ringtone? = null

    suspend fun getAllRingtones(): List<RingtoneEntity> {
        return getSystemRingtonesUseCase()
    }

    suspend fun getRingtoneByUri(uriPath: String): RingtoneEntity {
        val ringtones = getSystemRingtonesUseCase()
        val index = ringtones.indexOfFirst { it.uri.toString() == uriPath }
        return when (index) {
            -1 -> RingtoneEntity.asMute()
            0 -> RingtoneEntity.asDefault(ringtones.first())
            else -> ringtones[index]
        }
    }

    suspend fun getDefaultRingtone(): RingtoneEntity {
        val ringtones = getSystemRingtonesUseCase()
        return RingtoneEntity.asDefault(ringtones.first())
    }


    fun playRingtone(uri: Uri) {
        try {
            val ringtone: Ringtone = RingtoneManager.getRingtone(context, uri)
            currentRingtone = ringtone
            println("$TAG Play : $ringtone, $uri")
            ringtone.play()
        } catch (e: Exception) {
            println("$TAG Exception : ${e.printStackTrace()}, $uri")
            e.printStackTrace() // Handle cases where the ringtone URI is invalid or inaccessible
            currentRingtone = null
        }
    }

    fun startVibrating() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getVibrator().vibrate(
                VibrationEffect.createWaveform(
                    longArrayOf(1000, 1000, 1000, 1000),
                    -1
                )
            )
        } else {
            @Suppress("DEPRECATION")
            getVibrator().vibrate(longArrayOf(1000, 1000, 1000, 1000), -1)
        }
    }

    fun stopVibrating() {
        getVibrator().cancel()
    }

    private fun getVibrator(): Vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    fun stopRingtone() {
        currentRingtone?.stop()
        currentRingtone = null
    }

    companion object {
        const val TAG = "RingtonesManager"
    }

}
