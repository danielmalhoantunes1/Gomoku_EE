package pt.isel.pdm.gomoku_ee.Game

import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import pt.isel.pdm.gomoku_ee.GamePlayInputModel
import pt.isel.pdm.gomoku_ee.MakeButton
import pt.isel.pdm.gomoku_ee.ui.theme.Gomoku_EETheme
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    game: Game,
    onPlay: (GamePlayInputModel) -> Unit = {},
    onMainRequested: () -> Unit = {},
    onSurrender: () -> Unit = {}
) {
    var startt by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(true) }
    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(1000) // Delay de 1 segundo
            if (startt >= 30) {
                onSurrender()
                isRunning = false
            }else startt++
        }
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
                GameView(game, startt, onMainRequested) { input -> onPlay(input) }
            }
        }
    }
}
