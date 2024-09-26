package com.example.snakedae.ui.windowDraw

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class TableroDraw(
    private val screenWidth: Float,
    private val screenHeight: Float,
    private val numRows: Int,
    private val numCols: Int,
) {
    // Pinturas para los colores del mosaico
    private val whitePaint = Paint().apply {
        color = Color.WHITE
        strokeWidth = 5f
    }

    private val blackPaint = Paint().apply {
        color = Color.GRAY
        strokeWidth = 5f
    }

    fun drawBoard(canvas: Canvas) {
        val cellWidth = screenWidth / numCols
        val cellHeight = screenHeight / numRows

        for (row in 0 until numRows) {
            for (col in 0 until numCols) {
                val left = col * cellWidth
                val top = row * cellHeight
                val right = left + cellWidth
                val bottom = top + cellHeight

                // Alternar colores blanco y gris para crear un patrón de mosaico
                if ((row + col) % 2 == 0) {
                    canvas.drawRect(left, top, right, bottom, whitePaint)
                } else {
                    canvas.drawRect(left, top, right, bottom, blackPaint)
                }
            }
        }
    }
}