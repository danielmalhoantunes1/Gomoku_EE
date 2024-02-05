package pt.isel.pdm.gomoku_ee.Favourite

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.pdm.gomoku_ee.Game.GameScreen
import pt.isel.pdm.gomoku_ee.MainActivity
import pt.isel.pdm.gomoku_ee.MakeButton
import pt.isel.pdm.gomoku_ee.R
import pt.isel.pdm.gomoku_ee.ui.theme.Gomoku_EETheme

class FavouriteActivity : ComponentActivity() {
    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, FavouriteActivity::class.java)
            origin.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Gomoku_EETheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FavouriteScreen(
                        //onGameRequested = { GameActivity.navigateTo(this) },
                        //onFavouriteRequested = { FavouriteActivity.navigateTo(this) }
                    )
                }
            }
        }
    }
}

@Composable
fun FavouriteScreen(
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
        //MakeButton("Game") { onGameRequested() }
        MakeButton("Favourite Games") { onFavouriteRequested() }
    }

}

@Preview(showBackground = true)
@Composable
fun FavouritePreview() {
    Gomoku_EETheme {
        FavouriteScreen()
    }
}