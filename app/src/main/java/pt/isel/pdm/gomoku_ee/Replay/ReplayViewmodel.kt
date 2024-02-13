package pt.isel.pdm.gomoku_ee.Replay

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import pt.isel.pdm.gomoku_ee.Domain.Board
import pt.isel.pdm.gomoku_ee.Domain.CellState
import pt.isel.pdm.gomoku_ee.Domain.Game
import pt.isel.pdm.gomoku_ee.Domain.GameState
import java.util.UUID

class ReplayViewmodel(private val plays: Array<Pair<Int, Char>>) : ViewModel() {
    companion object {
        fun factory(plays: Array<Pair<Int, Char>>) = viewModelFactory {
            initializer { ReplayViewmodel(plays) }
        }
    }

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

    private fun paramsToGridValues(row: Int, col: Char) = Pair(row - 1, col.lowercaseChar() - 'A' - 32)

    var idx: Int by mutableStateOf(0)

    var lastpieceplaced :Boolean by mutableStateOf(false)

    fun nextPlay(input: Pair<Int, Char>, game: Game) {
        val gridValues = paramsToGridValues(input.first, input.second)
        val turn = game.board.getCurrTurn()
        gameBoard = gameBoard.processTurn(
            gridValues.first,
            gridValues.second,
            CellState.fromChar(turn),
            CellState.fromChar(turn).getNextTurn())
        if (idx == plays.size - 1) lastpieceplaced = true
        else lastpieceplaced = false
        idx = if (idx == plays.size - 1) idx  else idx + 1
    }

    fun prevPlay(input: Pair<Int, Char>, game: Game) {
        val gridValues = paramsToGridValues(input.first, input.second)
        val turn = game.board.getCurrTurn()
        gameBoard = gameBoard.unupdateGame(
            lastpieceplaced,
            CellState.fromChar(turn),
            CellState.fromChar(turn).getNextTurn(),
            gridValues.first,
            gridValues.second)
        idx = if (lastpieceplaced) {
            idx
        } else {
            if (idx == 0) idx else idx - 1
        }
        lastpieceplaced = false

    }
}
