package pt.isel.pdm.gomoku_ee.Domain

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class FavouriteGame(
    val name: String?,
    val opponent: String?,
    val date: LocalDate,
    var plays: Array<Pair<Int, Char>>
)
