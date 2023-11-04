package com.arnoract.piggiplanstation.ui.main.detail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.arnoract.piggiplanstation.R

@Composable
fun CircleExample(color: Int = R.color.black) {

    val colorFromResource = colorResource(id = color)

    Canvas(
        modifier = Modifier.size(20.dp)
    ) {
        // Define the circle's center and radius
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val radius = size.minDimension / 2f

        // Draw the circle
        drawCircle(
            color = colorFromResource, // Circle color
            center = Offset(centerX, centerY), // Center of the circle
            radius = radius - 4.dp.toPx(), // Subtract a small value for stroke width
            style = Stroke(2.dp.toPx()) // Stroke width
        )
    }
}