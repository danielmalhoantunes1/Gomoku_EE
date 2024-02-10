package pt.isel.pdm.gomoku_ee.Favourite

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.pdm.gomoku_ee.Game.GameScreen
import pt.isel.pdm.gomoku_ee.Game.GameViewModel
import pt.isel.pdm.gomoku_ee.MainActivity
import pt.isel.pdm.gomoku_ee.MakeButton
import pt.isel.pdm.gomoku_ee.R
import pt.isel.pdm.gomoku_ee.ui.theme.Gomoku_EETheme
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class FavouriteActivity : ComponentActivity() {
    private val viewModel by viewModels<FavouriteViewmodel>(
        factoryProducer = { FavouriteViewmodel.factory() }
    )
    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, FavouriteActivity::class.java)
            origin.startActivity(intent)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Gomoku_EETheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FavouriteScreen(
                        list = viewModel.favouriteGamesList,
                        onMainRequested = { MainActivity.navigateTo(this) }
                    )
                }
            }
        }
    }
}

@Composable
fun FavouriteScreen(
    list: List<FavouriteGame>,
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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        list.forEachIndexed { index, favouriteGame ->
            Button(onClick = {onReplayRequested(favouriteGame)}, modifier = Modifier.fillMaxWidth()) {
                Text("Game name: ${favouriteGame.name} | Opponent: ${favouriteGame.opponent} | \n Date: ${favouriteGame.date}")
            }
        }
        MakeButton("Main Page") { onMainRequested() }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun FavouritePreview() {
    val list: List<FavouriteGame>
    list = mutableListOf(
        FavouriteGame(
            name = "Game1",
            opponent = "Opponent1",
            date = LocalDate.now(),
            plays = emptyArray()
        ),
        FavouriteGame(
            name = "Game2",
            opponent = "Opponent2",
            date = LocalDate.now(),
            plays = emptyArray()
        )
    )
    Gomoku_EETheme {
        FavouriteScreen(list)
    }
}