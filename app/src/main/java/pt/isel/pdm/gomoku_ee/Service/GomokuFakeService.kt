package pt.isel.pdm.gomoku_ee.Service

import android.os.Build
import androidx.annotation.RequiresApi
import pt.isel.pdm.gomoku_ee.Domain.FavouriteGame

class GomokuFakeService: GomokuService{
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun updateFavInfo(game: FavouriteGame): FavouriteGame {
        return FavouritesGomoku.saveFavourite(game)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getFavourites(): List<FavouriteGame> {
        return FavouritesGomoku.favGames
    }
}

object FavouritesGomoku{
    @RequiresApi(Build.VERSION_CODES.O)
    private val favourites: MutableList<FavouriteGame> = mutableListOf()

    val favGames : List<FavouriteGame>
        @RequiresApi(Build.VERSION_CODES.O)
        get() = favourites.toList()

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveFavourite(game: FavouriteGame): FavouriteGame {
        val newFavGame = FavouriteGame(game.name, game.opponent, game.date, game.plays)

        favourites.add(newFavGame)

        return newFavGame
    }
}