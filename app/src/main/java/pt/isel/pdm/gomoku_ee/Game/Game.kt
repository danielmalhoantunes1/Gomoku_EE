package pt.isel.pdm.gomoku_ee.Game

import java.util.*

enum class GameState {
    STARTING,
    ON_GOING,
    FINISHED;

    override fun toString(): String {
        return when {
            this == STARTING -> "Starting"
            this == ON_GOING -> "On Going"
            this == FINISHED -> "Finished"
            else -> throw IllegalArgumentException("Invalid value for GameState")
        }
    }
    companion object {
        fun fromString(s: String) = when (s) {
            "Starting" -> STARTING
            "On Going" -> ON_GOING
            "Finished" -> FINISHED
            else -> throw IllegalArgumentException("Invalid value for GameState")
        }
    }
}

enum class CellState(val char: Char) {
    EMPTY('-'),
    PLAYER_B('B'),
    PLAYER_W('W');

    override fun toString() =
        if (this == PLAYER_B) "Black Player" else if (this == PLAYER_W) "White Player" else "Empty"

    fun getNextTurn() = if (this == PLAYER_B) 'W' else 'B'

    companion object {
        fun fromChar(c: Char) = when (c) {
            '-' -> EMPTY
            'B' -> PLAYER_B
            'W' -> PLAYER_W
            else -> throw IllegalArgumentException("Invalid value for Board.State")
        }
    }
}

data class Board(
    private val cells: Array<Array<CellState>>,
    private val size: Int,
    private val turn: Char
) {
    fun get(row: Int, col: Int) = cells[row][col]
    fun mutate(currTurn: CellState, nextTurn: Char, row: Int, col: Int): Board {
        val newBoardCells = Array(size) { r -> Array(size) { c -> cells[r][c] } }
        newBoardCells[row][col] = currTurn
        return Board(newBoardCells, size, nextTurn)
    }

    fun getNumberOfCells (): Int {
        var totalCells = 0
        cells.forEach { it.forEach { cell-> if (cell != CellState.EMPTY) totalCells++ } }
        return totalCells
    }

    fun checkIfWin(row: Int, col: Int): Boolean {
        val adjacentCells = arrayOf(
            Pair(-1, -1),
            Pair(-1, 0),
            Pair(-1, 1),
            Pair(0, -1),
            Pair(0, 1),
            Pair(1, -1),
            Pair(1, 0),
            Pair(1, 1)
        )
        adjacentCells.forEach {
            if (checkCellsInLine(row, col, it.first, it.second, 0, false) == 4) return true
        }
        return false
    }

    fun checkIfDraw() = getNumberOfCells() + 1 == size * size

    private fun checkCellsInLine(row: Int, col: Int, rowInc: Int, colInc: Int, currCount: Int, onRepeat: Boolean): Int {
        var currRow = row + rowInc
        var currCol = col + colInc
        var count = currCount
        while (
            currRow in 0 until size && currCol in 0 until size
            && get(currRow, currCol) == CellState.fromChar(turn)
        ) {
            count++
            currRow += rowInc
            currCol += colInc
        }
        return if (count <= 4 && !onRepeat) checkCellsInLine(row, col, -rowInc, -colInc, count, true)
        else count
    }

    fun getSize() = size
    fun getCurrTurn() = turn

    companion object {
        fun create(size: Int) =
            Board(Array(size) { Array(size) { CellState.EMPTY } }, size, 'B')
    }

    override fun toString(): String =
        cells.flatMap { row -> row.map { it.char } }.joinToString("") + "/$size/$turn"
}

data class Game(
    val id: UUID,
    val board: Board,
    val state: GameState,
    val playerB: Int,
    val playerW: Int,
    val winner: Int? = null
) {
    fun isFinished() = state == GameState.FINISHED

    fun processTurn(
        row: Int,
        col: Int,
        currTurn: CellState,
        nextTurn: Char
    ): Game {
        return if (board.checkIfWin(row, col)) winGame(currTurn, row, col)
        else if (board.checkIfDraw()) drawGame(currTurn, nextTurn, row, col)
        else updateGame(currTurn, nextTurn, row, col)
    }

    private fun updateGame(currTurn: CellState, nextTurn: Char, row: Int, col: Int): Game {
        val updatedGame = copy(board = this.board.mutate(currTurn, nextTurn, row, col))
        return updatedGame
    }

    private fun winGame(currTurn: CellState, row: Int, col: Int): Game {
        val winner = if (currTurn == CellState.PLAYER_B) 1 else 2
        val updatedGame = copy(
            board = this.board.mutate(currTurn, currTurn.char, row, col),
            state = GameState.FINISHED,
            winner = winner
        )
        return updatedGame
    }

    fun surrender(game: Game): Game {
        val winner = if (game.board.getCurrTurn() == 'B') 2 else 1
        val updatedGame = game.copy(
            state = GameState.FINISHED,
            winner = winner
        )
        return updatedGame
    }


    private fun drawGame(currTurn: CellState, nextTurn: Char, row: Int, col: Int): Game {
        val updatedGame = copy(board = this.board.mutate(currTurn, nextTurn, row, col), state = GameState.FINISHED)
        return updatedGame
    }
}