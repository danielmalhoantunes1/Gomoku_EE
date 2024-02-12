package pt.isel.pdm.gomoku_ee.Favourite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.isel.pdm.gomoku_ee.Domain.FavouriteGame

@Composable
fun FavouriteView(
    favourite: FavouriteGame,
    onReplayRequested: (FavouriteGame) -> Unit = { }
    ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onReplayRequested(favourite) }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
    ) {
            Text(text = "Game name: ${favourite.name}")
            Text(text = "Opponent: ${favourite.opponent}")
            Text(text = "Date: ${favourite.date}")
        }
    }
}