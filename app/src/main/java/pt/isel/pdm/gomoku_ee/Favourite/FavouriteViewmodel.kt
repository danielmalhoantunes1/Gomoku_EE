package pt.isel.pdm.gomoku_ee.Favourite

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import pt.isel.pdm.gomoku_ee.Game.GameViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class FavouriteViewmodel: ViewModel() {

    companion object {
        fun factory() = viewModelFactory {
            initializer { FavouriteViewmodel() }
        }
    }

    var newFavouriteGame by mutableStateOf(FavouriteGame(
        name = null,
        opponent = null,
        date = null,
        plays = emptyArray()
    ))

    var favouriteGamesList = mutableListOf<FavouriteGame>()

    fun addFavouriteGame(favouriteGame: FavouriteGame) {
        favouriteGamesList.add(favouriteGame)
    }
}