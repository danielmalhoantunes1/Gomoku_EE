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
import pt.isel.pdm.gomoku_ee.GamePlayInputModel
import pt.isel.pdm.gomoku_ee.LoadState
import pt.isel.pdm.gomoku_ee.resetToIdle
import java.util.UUID

class GameViewModel : ViewModel() {

    companion object {
        fun factory() = viewModelFactory {
            initializer { GameViewModel() }
        }
    }

    var gameBoard by mutableStateOf(Game(
            id = UUID.randomUUID(),
            board = Board.create(15),
            state = GameState.STARTING,
            playerB = 1,
            playerW = 2,
            winner = 0
    ))

    private val errorFlow = MutableStateFlow<LoadState<String>>(LoadState.Idle)

    private val createGameFlow: MutableStateFlow<LoadState<String>> = MutableStateFlow(LoadState.Idle)

    private val gameFlow: MutableStateFlow<LoadState<Game>> = MutableStateFlow(LoadState.Idle)

    private val gameIdFlow: MutableStateFlow<LoadState<String>> = MutableStateFlow(LoadState.Idle)

    private fun paramsToGridValues(row: Int, col: Char) = Pair(row - 1, col.lowercaseChar() - 'A' - 32)

    fun play(input: GamePlayInputModel, game: Game) {
        if (game.state != GameState.FINISHED) {
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

    val error: Flow<LoadState<String>>
        get() = errorFlow.asStateFlow()

    val createGame: Flow<LoadState<String>>
        get() = createGameFlow.asStateFlow()

    val game: Flow<LoadState<Game>>
        get() = gameFlow.asStateFlow()

    val gameId: Flow<LoadState<String>>
        get() = gameIdFlow.asStateFlow()

    fun resetStartGame() {
        createGameFlow.value = createGameFlow.value.resetToIdle()
    }

    fun errorToIdle() {
        errorFlow.value = errorFlow.value.resetToIdle()
    }
}

