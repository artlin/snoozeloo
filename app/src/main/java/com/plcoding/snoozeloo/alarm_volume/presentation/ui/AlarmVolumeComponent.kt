package com.plcoding.snoozeloo.alarm_volume.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.plcoding.snoozeloo.alarm_volume.presentation.AlarmVolumeSubState
import com.plcoding.snoozeloo.core.ui.text.TextTitle2Strong

@Composable
fun AlarmVolumeComponent(
    label: String,
    alarmVolumeSubState: AlarmVolumeSubState,
    onVolumeChanged: (Float) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextTitle2Strong(text = label, color = MaterialTheme.colorScheme.onSurface)
        CustomSlider(alarmVolumeSubState.currentVolume, onVolumeChanged)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSlider(progress: Float, onChanged: (Float) -> Unit) {

    val sizes = 36.dp
    var sliderValue by remember { mutableFloatStateOf(progress) }
    // State to track if thumb is pressed
    var isThumbPressed by remember { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }

    // Listen to interactions globally
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> isThumbPressed = true
                is PressInteraction.Release, is DragInteraction.Cancel -> isThumbPressed = false
                is DragInteraction.Start -> isThumbPressed = true
            }
        }
    }

    Slider(
        value = sliderValue,
        onValueChange = { newValue ->
            sliderValue = newValue // Update the slider value
            onChanged(newValue)
        },
        valueRange = 0f..1f,
        steps = 32,
        track = { CustomSliderTrack(sliderValue) },
        thumb = { CustomSliderThumb(isThumbPressed) },
        interactionSource = interactionSource,
        modifier = Modifier
            .padding(vertical = 2.dp)
    )
}

@Composable
fun CustomSliderThumb(isThumbPressed: Boolean) {
    val color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f)
    val alpha = if (isThumbPressed) 1f else 0f
    Box(contentAlignment = Alignment.Center) {

        Box(
            Modifier
                .size(16.dp)
                .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
        )

    }
}

@Composable
fun CustomSliderTrack(sliderProgress: Float) {
    Log.e("SliderState", sliderProgress.toString())
    val height = 6.dp
    val shape = RoundedCornerShape(8.dp)
    val staticBg = Modifier
        .fillMaxWidth()
        .height(height)
        .background(color = Color(0xFFECEFFF), shape = shape)
    val movableBg = Modifier
        .fillMaxWidth(sliderProgress)
        .height(height)
        .background(color = MaterialTheme.colorScheme.primary, shape = shape)

    Box(
        modifier = staticBg
    ) {
        Box(modifier = movableBg)
    }
}
