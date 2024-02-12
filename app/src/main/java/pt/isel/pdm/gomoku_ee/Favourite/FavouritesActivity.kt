package pt.isel.pdm.gomoku_ee.Favourite

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import pt.isel.pdm.gomoku_ee.GomokuDependenciesContainer
import pt.isel.pdm.gomoku_ee.Domain.LoadState
import pt.isel.pdm.gomoku_ee.Main.MainActivity
import pt.isel.pdm.gomoku_ee.Replay.ReplayActivity
import pt.isel.pdm.gomoku_ee.ui.theme.Gomoku_EETheme

@RequiresApi(Build.VERSION_CODES.O)
class FavouriteActivity : ComponentActivity() {
    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, FavouriteActivity::class.java)
            origin.startActivity(intent)
        }
    }

    private val dependecies by lazy {
        application as GomokuDependenciesContainer
    }

    private val viewModel by viewModels<FavouriteViewmodel>(
        factoryProducer = { FavouriteViewmodel.factory(dependecies.gomokuService) }
    )


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.getFavourites()
        }
        setContent {
            Gomoku_EETheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val currentFavourites by viewModel.favourites.collectAsState(initial = LoadState.Idle)
                    FavouriteScreen(
                        list = currentFavourites,
                        onMainRequested = { MainActivity.navigateTo(this) },
                        onReplayRequested = { favouriteinfo -> ReplayActivity.navigateTo(this, FavouriteExtra(favouriteinfo)) }
                    )
                }
            }
        }
    }
}
