package pt.isel.pdm.gomoku_ee.Domain

import java.util.*

data class Game(
    val id: UUID,
    val board: Board,
    val state: GameState,
    val playerB: Int,
    val playerW: Int,
    val winner: Int? = null
) {
    fun isFinished():Boolean{
        return state == GameState.FINISHED
    }

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
        return copy(board = board.mutate(currTurn, nextTurn, row, col))
    }

    fun unupdateGame(lastpieceplaced: Boolean,currTurn: CellState, nextTurn: Char, row: Int, col: Int): Game {
        return copy(board = board.unmutate(lastpieceplaced, currTurn, nextTurn, row, col))
    }

    private fun winGame(currTurn: CellState, row: Int, col: Int): Game {
        val winner = if (currTurn == CellState.PLAYER_B) 1 else 2
        return copy(
            board = board.mutate(currTurn, currTurn.char, row, col),
            state = GameState.FINISHED,
            winner = winner
        )
    }

    fun surrender(game: Game): Game {
        val winner = if (game.board.getCurrTurn() == 'B') 2 else 1
        return game.copy(
            state = GameState.FINISHED,
            winner = winner
        )
    }


    private fun drawGame(currTurn: CellState, nextTurn: Char, row: Int, col: Int): Game {
        return copy(board = board.mutate(currTurn, nextTurn, row, col), state = GameState.FINISHED)
    }
}
