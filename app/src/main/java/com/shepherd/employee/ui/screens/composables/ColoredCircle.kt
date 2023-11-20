package com.shepherd.employee.ui.screens.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColoredCircle(hexColor: String) {
    val color = Color(android.graphics.Color.parseColor(hexColor))
    Box(modifier = Modifier.size(30.dp)) {
        Canvas(
            modifier = Modifier.fillMaxSize(),
        ) {
            drawCircle(
                color = color,
                center = center,
                radius = size.minDimension / 2,
            )
        }
    }
}
