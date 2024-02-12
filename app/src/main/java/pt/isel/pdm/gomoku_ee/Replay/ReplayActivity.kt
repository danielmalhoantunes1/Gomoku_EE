package pt.isel.pdm.gomoku_ee.Replay

import android.content.Context
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
import androidx.compose.ui.Modifier
import pt.isel.pdm.gomoku_ee.Favourite.FAVOURITE_EXTRA
import pt.isel.pdm.gomoku_ee.Favourite.FavouriteExtra
import pt.isel.pdm.gomoku_ee.Domain.FavouriteGame
import pt.isel.pdm.gomoku_ee.Favourite.getFavInfoExtra
import pt.isel.pdm.gomoku_ee.Favourite.toFavouriteGame
import pt.isel.pdm.gomoku_ee.Main.MainActivity
import pt.isel.pdm.gomoku_ee.ui.theme.Gomoku_EETheme

@RequiresApi(Build.VERSION_CODES.O)
class ReplayActivity : ComponentActivity() {
    private val viewModel by viewModels<ReplayViewmodel>{
        ReplayViewmodel.factory(favouriteInfo.plays)
    }

    companion object {
        fun navigateTo(origin: ComponentActivity, favouriteextra: FavouriteExtra) {
            origin.startActivity(createIntent(origin, favouriteextra))
        }

        private fun createIntent(context: Context, favouriteextra: FavouriteExtra): Intent {
            val intent = Intent(context, ReplayActivity::class.java)
            intent.putExtra(FAVOURITE_EXTRA, favouriteextra)
            return intent
        }
    }

    private val favouriteInfo: FavouriteGame by lazy {
        checkNotNull(getFavInfoExtra(intent)).toFavouriteGame()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Gomoku_EETheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ReplayScreen(
                        favouriteInfo = favouriteInfo,
                        idx = viewModel.idx,
                        game = viewModel.gameBoard,
                        lastpieceplaced = viewModel.lastpieceplaced,
                        onMainRequested = { MainActivity.navigateTo(this) },
                        onNext = {viewModel.nextPlay(favouriteInfo.plays[viewModel.idx],viewModel.gameBoard)},
                        onPrev = {viewModel.prevPlay(favouriteInfo.plays[viewModel.idx-1],viewModel.gameBoard)},
                        onLast = {viewModel.prevPlay(favouriteInfo.plays[viewModel.idx],viewModel.gameBoard)}
                    )
                }
            }
        }
    }
}
