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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.pdm.gomoku_ee.Favourite.FavouriteViewmodel
import pt.isel.pdm.gomoku_ee.MainActivity
import pt.isel.pdm.gomoku_ee.ui.theme.Gomoku_EETheme
import java.util.UUID

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
        setContent {
            Gomoku_EETheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GameScreen(
                        game = viewModel.gameBoard,
                        onPlay = { input -> viewModel.play(input, viewModel.gameBoard) },
                        onMainRequested = { MainActivity.navigateTo(this)},
                        onSurrender = { viewModel.surrender() }
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