package pt.isel.pdm.gomoku_ee.Game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pt.isel.pdm.gomoku_ee.Domain.Board
import pt.isel.pdm.gomoku_ee.Domain.CellState
import pt.isel.pdm.gomoku_ee.Domain.Game
import pt.isel.pdm.gomoku_ee.Domain.GameState
import pt.isel.pdm.gomoku_ee.Domain.GamePlayInputModel
import java.util.UUID

class GameViewModel : ViewModel() {

    companion object {
        fun factory() = viewModelFactory {
            initializer { GameViewModel() }
        }
    }

    private var timer: Long by mutableStateOf(30)
    private var isRunning: Boolean by mutableStateOf(false)

    val time: Long
        get() = timer

    var gameBoard by mutableStateOf(
        Game(
            id = UUID.randomUUID(),
            board = Board.create(15),
            state = GameState.STARTING,
            playerB = 1,
            playerW = 2,
            winner = 0
    )
    )

    var playList: Array<Pair<Int, Char>> = emptyArray()

    fun startTimer() {
        timer = 0
        isRunning = true
        viewModelScope.launch {
            while (isRunning) {
                delay(1000) // Delay de 1 segundo
                if (timer >= 30) {
                    isRunning = false
                    surrender()
                }
                timer++
            }
        }
    }

    private fun resetTimer() {
        timer = 0
    }

    private fun surrender() {
        gameBoard = gameBoard.surrender(gameBoard)
    }

    private fun paramsToGridValues(row: Int, col: Char) = Pair(row - 1, col.lowercaseChar() - 'A' - 32)

    fun play(input: GamePlayInputModel, game: Game) {
        if (game.state != GameState.FINISHED) {
            resetTimer()
            val gridValues = paramsToGridValues(input.row, input.col)
            val turn = game.board.getCurrTurn()
            playList += Pair(input.row, input.col)
            gameBoard = gameBoard.processTurn(
                gridValues.first,
                gridValues.second,
                CellState.fromChar(turn),
                CellState.fromChar(turn).getNextTurn()
            )
        }
    }
}
