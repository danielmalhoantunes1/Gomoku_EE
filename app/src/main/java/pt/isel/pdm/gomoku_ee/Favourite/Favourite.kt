package pt.isel.pdm.gomoku_ee.Favourite

import pt.isel.pdm.gomoku_ee.GamePlayInputModel
import java.time.LocalDate

data class FavouriteGame(
    val name: String?,
    val opponent: String?,
    val date: LocalDate?,
    var plays: Array<Pair<Int, Char>>
) {
    fun addPlay(play: GamePlayInputModel) {
        plays += Pair(play.row, play.col)
    }

    fun create(name: String, opponent: String, date: LocalDate) = FavouriteGame(name, opponent, date, emptyArray())
}