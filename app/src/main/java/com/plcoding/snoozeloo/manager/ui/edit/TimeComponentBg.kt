package com.plcoding.snoozeloo.manager.ui.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.TimeComponentBg(onClick: OnClick, content: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .weight(1f)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(vertical = 16.dp, horizontal = 36.dp)
            .clickable { onClick() }, horizontalArrangement = Arrangement.Center
    ) {
        content()
    }
}