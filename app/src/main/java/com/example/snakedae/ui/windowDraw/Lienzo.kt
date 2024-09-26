package com.example.snakedae.ui.windowDraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.snakedae.db.FruitNp
import com.example.snakedae.db.SnakePj

class Lienzo(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val tablet = TableroDraw(
            1080f,
            1920f,
            16,
            9)

    private val snake = SnakePj(
        1080f,
        1920f,
        16,
        9,
        true,
        120f)

    private val fruit = FruitNp(
        16,
        9,
        120f)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        tablet.drawBoard(canvas)
        if (snake.isSnakeAlive()){
            val screenWidth = width.toFloat()
            val screenHeight = height.toFloat()
            snake.drawSnake(canvas)
            fruit.drawFruit(canvas, screenWidth, screenHeight) // Aquí se dibuja la fruta
            snake.move()
            if (snake.checkFruitCollision(fruit)) {
                // Genera una nueva fruta si se ha comido la anterior
                fruit.position = fruit.generateNewPosition()
            }
            postInvalidateDelayed(200)
        }else{
            val paint = Paint().apply {
                color = Color.RED
                textSize = 100f
                textAlign = Paint.Align.CENTER
            }
            canvas.drawText("Game Over", width / 2f, height / 2f, paint)
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        // Calcula los límites para las secciones
        val arribaLimit = height * 0.2f
        val abajoStart = height * 0.8f
        val midWidth = width * 0.5f
        val midHeightStart = height * 0.2f
        val midHeightEnd = height * 0.8f

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                when {
                    // Detección de la sección ARRIBA
                    y < arribaLimit -> {
                        snake.setDirection(SnakePj.Direction.UP)
                    }
                    // Detección de la sección ABAJO
                    y > abajoStart -> {
                        snake.setDirection(SnakePj.Direction.DOWN)
                    }
                    // Detección de la sección IZQUIERDA (parte media)
                    x < midWidth && y in midHeightStart..midHeightEnd -> {
                        snake.setDirection(SnakePj.Direction.LEFT)
                    }
                    // Detección de la sección DERECHA (parte media)
                    x > midWidth && y in midHeightStart..midHeightEnd -> {
                        snake.setDirection(SnakePj.Direction.RIGHT)
                    }
                }
            }
        }
        return true
    }
}