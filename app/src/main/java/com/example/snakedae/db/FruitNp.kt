package com.example.snakedae.db

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class FruitNp(
    private val numRows: Int,
    private val numCols: Int,
    private val sizeCorps: Float
) {
    private val fruitPaint = Paint().apply {
        color = Color.RED // Color de la fruta
    }

    // Posición actual de la fruta (fila y columna)
    var position: Pair<Int, Int> = generateNewPosition()

    // Método para generar una nueva posición aleatoria para la fruta
    fun generateNewPosition(): Pair<Int, Int> {
        val row = (0 until numRows).random()
        val col = (0 until numCols).random()
        return Pair(row, col)
    }

    // Dibuja la fruta en el canvas
    fun drawFruit(canvas: Canvas, screenWidth: Float, screenHeight: Float) {
        // Calcula el tamaño de cada celda
        val cellWidth = screenWidth / numCols
        val cellHeight = screenHeight / numRows

        // Dibuja la fruta en su posición actual
        val (row, col) = position
        val left = col * cellWidth
        val top = row * cellHeight
        val right = left + cellWidth
        val bottom = top + cellHeight
        canvas.drawRect(left, top, right, bottom, fruitPaint)
    }
}