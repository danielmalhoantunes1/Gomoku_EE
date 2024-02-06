package pt.isel.pdm.gomoku_ee.Game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import pt.isel.pdm.gomoku_ee.GamePlayInputModel
import pt.isel.pdm.gomoku_ee.MakeButton
import pt.isel.pdm.gomoku_ee.ui.theme.Gomoku_EETheme
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    onPlay: (GamePlayInputModel) -> Unit = {},
    onMainRequested: () -> Unit = {}
) {
    var game by remember {
        mutableStateOf(
            Game(
                id = UUID.randomUUID(),
                board = Board.create(15),
                state = GameState.STARTING,
                playerB = 1,
                playerW = 2,
                winner = 0
            )
        )
    }
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
                GameView(game) { input -> onPlay(input) }
                MakeButton(text = "Main Menu") { onMainRequested() }
            }
        }
    }
}
