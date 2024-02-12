package pt.isel.pdm.gomoku_ee.Main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pt.isel.pdm.gomoku_ee.Favourite.FavouriteActivity
import pt.isel.pdm.gomoku_ee.Game.GameActivity
import pt.isel.pdm.gomoku_ee.R
import pt.isel.pdm.gomoku_ee.ui.theme.Gomoku_EETheme

class MainActivity : ComponentActivity() {
    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, MainActivity::class.java)
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
                    MainScreen(
                        onGameRequested = { GameActivity.navigateTo(this) },
                        onFavouriteRequested = { FavouriteActivity.navigateTo(this) }
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
                onGameRequested: () -> Unit = { },
                onFavouriteRequested: () -> Unit = { }
                ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background_wood),
                contentScale = ContentScale.FillBounds
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MakeButton("Game") { onGameRequested() }
        MakeButton("Favourite Games") { onFavouriteRequested() }
    }
}

@Composable
fun MakeButton(text: String, size: Dp = 200.dp, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .width(size),
        onClick = onClick
    ) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Gomoku_EETheme {
        MainScreen()
    }
}