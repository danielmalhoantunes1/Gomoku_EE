package pt.isel.pdm.gomoku_ee.Game

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.pdm.gomoku_ee.AddFavourite.AddFavouriteActivity
import pt.isel.pdm.gomoku_ee.Main.MainActivity
import pt.isel.pdm.gomoku_ee.ui.theme.Gomoku_EETheme

@RequiresApi(Build.VERSION_CODES.O)
class GameActivity : ComponentActivity() {
    private val viewModel by viewModels<GameViewModel>(
        factoryProducer = { GameViewModel.factory() }
    )
    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, GameActivity::class.java)
            origin.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.startTimer()
        setContent {
            Gomoku_EETheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GameScreen(
                        game = viewModel.gameBoard,
                        timer = viewModel.time,
                        onPlay = { input -> viewModel.play(input, viewModel.gameBoard)},
                        onMainRequested = { MainActivity.navigateTo(this)},
                        onAddToFavourites = { AddFavouriteActivity.navigateTo(this, viewModel.playList)}
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GamePreview() {
    Gomoku_EETheme {
        GameActivity()
    }
}