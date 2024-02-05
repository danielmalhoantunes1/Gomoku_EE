package pt.isel.pdm.gomoku_ee.Game

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.pdm.gomoku_ee.MainActivity
import pt.isel.pdm.gomoku_ee.ui.theme.Gomoku_EETheme

class GameActivity : ComponentActivity() {
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
                        onPlay = {},
                        onMainRequested = { MainActivity.navigateTo(this) },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GamePreview() {
    Gomoku_EETheme {
        GameScreen(onPlay = {})
    }
}