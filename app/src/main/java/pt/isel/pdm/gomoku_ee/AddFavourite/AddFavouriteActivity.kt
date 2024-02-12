package pt.isel.pdm.gomoku_ee.AddFavourite

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
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import pt.isel.pdm.gomoku_ee.GomokuDependenciesContainer
import pt.isel.pdm.gomoku_ee.Domain.LoadState
import pt.isel.pdm.gomoku_ee.Main.MainActivity
import pt.isel.pdm.gomoku_ee.ui.theme.Gomoku_EETheme

@RequiresApi(Build.VERSION_CODES.O)
class AddFavouriteActivity() : ComponentActivity() {
    companion object {
        fun navigateTo(origin: ComponentActivity, plays: Array<Pair<Int, Char>>) {
            origin.startActivity(createIntent(origin, plays))
        }

        private fun createIntent(context: Context, plays: Array<Pair<Int, Char>>): Intent {
            val intent = Intent(context, AddFavouriteActivity::class.java)
            intent.putExtra("PlayList", plays)
            return intent
        }
    }

    @Suppress("DEPRECATION")
    private val plays: Array<Pair<Int, Char>> by lazy{
        intent.getSerializableExtra("PlayList") as Array<Pair<Int, Char>>
    }

    private val viewModel by viewModels<AddFavouriteViewmodel>{
        AddFavouriteViewmodel.factory(dependencies.gomokuService)
    }

    private val dependencies by lazy {
        application as GomokuDependenciesContainer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            viewModel.favStateFlow.collect{
                if (it is LoadState.Loaded){
                    finish()
                }
            }
        }
        super.onCreate(savedInstanceState)
        setContent {
            Gomoku_EETheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AddFavouriteScreen(
                        plays = plays,
                        addFavourite = {fav -> viewModel.addFavouriteGame(fav); MainActivity.navigateTo(this)}
                    )
                }
            }
        }
    }
}
