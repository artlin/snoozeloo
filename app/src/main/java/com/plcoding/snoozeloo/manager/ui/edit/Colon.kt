package com.plcoding.snoozeloo.manager.ui.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Colon() {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Dot()
        Dot()
    }
}

@Composable
private fun Dot() {
    Box(
        Modifier
            .size(4.dp)
            .background(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                shape = CircleShape
            )
    )
}
