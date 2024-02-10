package pt.isel.pdm.gomoku_ee.Replay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import pt.isel.pdm.gomoku_ee.Game.GameViewModel

class ReplayViewmodel : ViewModel() {
    companion object {
        fun factory() = viewModelFactory {
            initializer { GameViewModel() }
        }
    }
}
