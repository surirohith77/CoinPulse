package com.rohith.cryptotracker.core.designsystem.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.rohith.cryptotracker.core.designsystem.theme.ChartGridLine

/**
 * A custom-drawn smooth cubic Bezier line chart.
 * Renders pricing data with a vertical gradient fill and gridlines.
 */
@Composable
fun LineChart(
    prices: List<Double>,
    modifier: Modifier = Modifier,
    lineColor: Color = Color(0xFF10B981),
    gradientColor: Color = Color(0xFF10B981)
) {
    if (prices.size < 2) return

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        val maxPrice = prices.maxOrNull() ?: 1.0
        val minPrice = prices.minOrNull() ?: 0.0
        val range = if (maxPrice == minPrice) 1.0 else maxPrice - minPrice

        val points = prices.mapIndexed { index, price ->
            val x = index.toFloat() / (prices.size - 1) * width
            // Invert Y coordinate since Canvas 0 is at top
            val y = height - ((price - minPrice) / range * height).toFloat()
            Offset(x, y)
        }

        // Draw horizontal grid lines
        val gridLinesCount = 4
        for (i in 0..gridLinesCount) {
            val y = (height / gridLinesCount) * i
            drawLine(
                color = ChartGridLine,
                start = Offset(0f, y),
                end = Offset(width, y),
                strokeWidth = 1.dp.toPx()
            )
        }

        // Draw vertical grid lines
        val verticalGridLinesCount = 5
        for (i in 0..verticalGridLinesCount) {
            val x = (width / verticalGridLinesCount) * i
            drawLine(
                color = ChartGridLine,
                start = Offset(x, 0f),
                end = Offset(x, height),
                strokeWidth = 1.dp.toPx()
            )
        }

        // Generate bezier path
        val path = Path().apply {
            moveTo(points[0].x, points[0].y)
            for (i in 1 until points.size) {
                val p0 = points[i - 1]
                val p1 = points[i]
                
                // Calculate control points for smooth Bezier spline
                val controlPointX1 = (p0.x + p1.x) / 2
                val controlPointY1 = p0.y
                val controlPointX2 = (p0.x + p1.x) / 2
                val controlPointY2 = p1.y
                
                cubicTo(
                    controlPointX1, controlPointY1,
                    controlPointX2, controlPointY2,
                    p1.x, p1.y
                )
            }
        }

        // Generate closed path for gradient fill under the line
        val fillPath = Path().apply {
            addPath(path)
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }

        // Draw gradient fill
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    gradientColor.copy(alpha = 0.35f),
                    gradientColor.copy(alpha = 0.05f),
                    Color.Transparent
                ),
                startY = 0f,
                endY = height
            )
        )

        // Draw main trend stroke line
        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 3.dp.toPx())
        )
    }
}
