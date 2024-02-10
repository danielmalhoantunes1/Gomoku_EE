package pt.isel.pdm.gomoku_ee.Replay

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import pt.isel.pdm.gomoku_ee.Favourite.FavouriteActivity
import pt.isel.pdm.gomoku_ee.Favourite.FavouriteGame
import pt.isel.pdm.gomoku_ee.Favourite.FavouriteViewmodel

/*class ReplayActivity {
    companion object {
        fun navigateTo(origin: ComponentActivity, favextra: FavouriteGame) {
            origin.startActivity(createIntent(origin, favextra))
        }

        private fun createIntent(context: Context, favextra: FavouriteGame): Intent {
            val intent = Intent(context, ReplayActivity::class.java)
            intent.putExtra(FAVOURITE_EXTRA, favextra)
            return intent
        }
    }

    private val viewModel by viewModels<ReplayViewmodel>(
        factoryProducer = { ReplayViewmodel.factory() }
    )
}*/
