package com.plcoding.snoozeloo.manager.ui.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.plcoding.snoozeloo.core.ui.text.TextBody
import com.plcoding.snoozeloo.core.ui.text.TextH2

@Composable
fun EditableClockComponent() {
    Column(
        Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(10.dp))
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TimeComponent()
            Colon()
            TimeComponent()
        }
        TextBody(
            text = "Alarm in Xh Ymin",
            color = Color(0xFF858585)
        )
    }
}

@Composable
private fun TimeComponent() {
    Box(
        modifier = Modifier
            .background(color = Color(0xFFF6F6F6), shape = RoundedCornerShape(10.dp))
            .padding(vertical = 16.dp, horizontal = 36.dp),
        contentAlignment = Alignment.Center
    ) {
        TextH2(text = "00", color = Color(0xFF4664FF))
    }
}

@Composable
fun Colon() {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Dot()
        Dot()
    }
}

@Composable
fun Dot() {
    Box(
        Modifier
            .size(4.dp)
            .background(
                color = Color(0xFF858585),
                shape = CircleShape
            )
    )
}
