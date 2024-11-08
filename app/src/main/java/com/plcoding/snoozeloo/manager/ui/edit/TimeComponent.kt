package com.plcoding.snoozeloo.manager.ui.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.plcoding.snoozeloo.core.ui.text.TextH2

@Composable
fun TimeComponent(enabled: Boolean) {
    Box(
        modifier = Modifier
            .background(color = Color(0xFFF6F6F6), shape = RoundedCornerShape(10.dp))
            .padding(vertical = 16.dp, horizontal = 36.dp),
        contentAlignment = Alignment.Center
    ) {
        TextH2(text = "00", color = Color(if (enabled) 0xFF4664FF else 0xFF858585))
    }
}