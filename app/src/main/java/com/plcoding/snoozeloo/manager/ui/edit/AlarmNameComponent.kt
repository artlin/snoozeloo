package com.plcoding.snoozeloo.manager.ui.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.snoozeloo.core.ui.text.TextBody
import com.plcoding.snoozeloo.core.ui.text.TextTitle2Strong

@Composable
fun AlarmNameComponent() {
    Row(
        Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextTitle2Strong(text = "Alarm Name", color = MaterialTheme.colorScheme.onSurface)
        Spacer(Modifier.weight(1f))
        TextBody(text = "Work", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}