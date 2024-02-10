package pt.isel.pdm.gomoku_ee.Game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pt.isel.pdm.gomoku_ee.GamePlayInputModel
import pt.isel.pdm.gomoku_ee.LoadState
import java.util.UUID

class GameViewModel : ViewModel() {

    companion object {
        fun factory() = viewModelFactory {
            initializer { GameViewModel() }
        }
    }

    private val createGameFlow: MutableStateFlow<LoadState<String>> = MutableStateFlow(LoadState.Idle)

    private var timer: Long by mutableStateOf(30)
    private var isRunning: Boolean by mutableStateOf(false)

    val time: Long
        get() = timer

    val running
        get() = isRunning

    var gameBoard by mutableStateOf(Game(
            id = UUID.randomUUID(),
            board = Board.create(15),
            state = GameState.STARTING,
            playerB = 1,
            playerW = 2,
            winner = 0
    ))

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

    fun resetTimer() {
        timer = 0
    }

    fun surrender() {
        gameBoard = gameBoard.surrender(gameBoard)
    }

    private fun paramsToGridValues(row: Int, col: Char) = Pair(row - 1, col.lowercaseChar() - 'A' - 32)

    fun play(input: GamePlayInputModel, game: Game) {
        createGame
        if (game.state != GameState.FINISHED) {
            resetTimer()
            val gridValues = paramsToGridValues(input.row, input.col)
            val turn = game.board.getCurrTurn()
            gameBoard = gameBoard.processTurn(
                gridValues.first,
                gridValues.second,
                CellState.fromChar(turn),
                CellState.fromChar(turn).getNextTurn()
            )
        }
    }

    val createGame: Flow<LoadState<String>>
        get() = createGameFlow.asStateFlow()
}

