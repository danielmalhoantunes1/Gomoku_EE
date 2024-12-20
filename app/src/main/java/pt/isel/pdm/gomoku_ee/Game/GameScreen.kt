package pt.isel.pdm.gomoku_ee.Game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import pt.isel.pdm.gomoku_ee.Domain.Game
import pt.isel.pdm.gomoku_ee.Domain.GamePlayInputModel
import pt.isel.pdm.gomoku_ee.ui.theme.Gomoku_EETheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    game: Game,
    timer: Long,
    onPlay: (GamePlayInputModel) -> Unit = {},
    onMainRequested: () -> Unit = {},
    onAddToFavourites: () -> Unit = {}
) {

    Gomoku_EETheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
        ) { innerPadding ->
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                GameView(game, timer, onMainRequested, onAddToFavourites) { input -> onPlay(input)}
            }
        }
    }
}
