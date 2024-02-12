package pt.isel.pdm.gomoku_ee.Favourite

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import pt.isel.pdm.gomoku_ee.Domain.FavouriteGame
import pt.isel.pdm.gomoku_ee.Domain.LoadState
import pt.isel.pdm.gomoku_ee.Main.MakeButton
import pt.isel.pdm.gomoku_ee.R
import pt.isel.pdm.gomoku_ee.Domain.getOrNull

@Composable
fun FavouriteScreen(
    list: LoadState<List<FavouriteGame>>,
    onMainRequested: () -> Unit = { },
    onReplayRequested: (FavouriteGame) -> Unit = { }
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background_wood),
                contentScale = ContentScale.FillBounds
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (list) {
            is LoadState.Loaded -> {
        list.getOrNull()?.forEach { favouriteGame ->
            FavouriteView(favouriteGame) {favourite -> onReplayRequested(favourite)}
            Log.v("FavouriteScreen", "FavouriteScreen: ${favouriteGame.name}")
                }
                MakeButton(text = "Main Page") { onMainRequested() }
            }
            else -> {
                MakeButton(text = "Main Page") { onMainRequested() }
            }
        }
    }
}