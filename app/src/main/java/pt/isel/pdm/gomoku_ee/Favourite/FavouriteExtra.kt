package pt.isel.pdm.gomoku_ee.Favourite

import android.content.Intent
import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import java.time.LocalDate
import kotlinx.parcelize.Parcelize
import pt.isel.pdm.gomoku_ee.Domain.FavouriteGame

const val FAVOURITE_EXTRA = "favouriteInfo"
@Parcelize
data class FavouriteExtra(val title: String, val opponent: String, val date : LocalDate, val plays : Array<Pair<Int, Char>>) :Parcelable {
    constructor(favouriteInfo: FavouriteGame) : this(favouriteInfo.name!!, favouriteInfo.opponent!!, favouriteInfo.date, favouriteInfo.plays)
}

@RequiresApi(Build.VERSION_CODES.O)
fun FavouriteExtra.toFavouriteGame() : FavouriteGame {
    return FavouriteGame(title,opponent, date, plays)
}

@Suppress("DEPRECATION")
fun getFavInfoExtra(intent: Intent): FavouriteExtra? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        intent.getParcelableExtra(FAVOURITE_EXTRA, FavouriteExtra::class.java)
    else
        intent.getParcelableExtra(FAVOURITE_EXTRA)
