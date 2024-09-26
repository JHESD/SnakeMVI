package com.example.snakedae.db

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class SnakePj(
    // Posición inicial de la serpiente
    private val screenWidth: Float,
    private val screenHeight: Float,
    private val numRows: Int,
    private val numCols: Int,
    // Tamaño de la serpiente
    private var life: Boolean,
    private val sizeCorps: Float
) {
    private val snakePaint = Paint().apply {
        color = Color.GREEN
        strokeWidth = 5f
    }
    private var direction = Direction.RIGHT
    private var growing = false
    enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }
    private var snakeBody: MutableList<Pair
        <Int, Int>
            > = mutableListOf()

    init {
        val initialRow = numRows / 2
        val initialCol = numCols / 2
        snakeBody.add(Pair(initialRow, initialCol))
        snakeBody.add(Pair(initialRow, initialCol - 1))
        snakeBody.add(Pair(initialRow, initialCol - 2))
    }

    fun drawSnake(canvas: Canvas) {
        if (!life) return  // Si la serpiente está muerta, no la dibujes
        val cellWidth = screenWidth / numCols
        val cellHeight = screenHeight / numRows

        for (segment in snakeBody) {
            val (row, col) = segment
            val left = col * cellWidth
            val top = row * cellHeight
            val right = left + cellWidth
            val bottom = top + cellHeight
            canvas.drawRect(left, top, right, bottom, snakePaint)
        }
    }

    fun move() {
        val head = snakeBody.first()

        // Nueva cabeza basada en la dirección
        val newHead = when (direction) {
            Direction.UP -> Pair((head.first - 1 + numRows) % numRows, head.second)
            Direction.DOWN -> Pair((head.first + 1) % numRows, head.second)
            Direction.LEFT -> Pair(head.first, (head.second - 1 + numCols) % numCols)
            Direction.RIGHT -> Pair(head.first, (head.second + 1) % numCols)
        }

        // Añadir la nueva cabeza al frente del cuerpo
        snakeBody.add(0, newHead)

        // Si no estamos en "modo crecimiento", eliminamos el último segmento (movimiento normal)
        if (!growing) {
            snakeBody.removeLast()  // Mueve sin crecer
        } else {
            // Si estamos en "modo crecimiento", no eliminamos el último segmento (crecimiento)
            growing = false  // Después de crecer una vez, volvemos a movimiento normal
        }

        // Verificar si la serpiente colisiona consigo misma
        if (checkSelfCollision()) {
            life = false  // Si colisiona, muere
        }
    }

    fun setDirection(newDirection: Direction) {
        if ((direction == Direction.UP && newDirection != Direction.DOWN) ||
            (direction == Direction.DOWN && newDirection != Direction.UP) ||
            (direction == Direction.LEFT && newDirection != Direction.RIGHT) ||
            (direction == Direction.RIGHT && newDirection != Direction.LEFT)) {
            direction = newDirection
        }
    }
    private fun checkSelfCollision(): Boolean {
        if (snakeBody.size < 4){
            return false
        }
        val head = snakeBody[0]
        for (i in 1 until snakeBody.size) {
            if (snakeBody[i] == head) {
                return true
            }
        }
        return false
    }

    fun isSnakeAlive(): Boolean {
        return life
    }

    fun checkFruitCollision(fruit: FruitNp): Boolean {
        val head = snakeBody.first()
        if (head == fruit.position) {
            growing = true
            return true
        }
        return false
    }
}