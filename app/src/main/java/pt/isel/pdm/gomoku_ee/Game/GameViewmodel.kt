package pt.isel.pdm.gomoku_ee.Game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import pt.isel.pdm.gomoku_ee.LoadState
import pt.isel.pdm.gomoku_ee.resetToIdle
import java.util.UUID

class GameViewModel(private val service: GamesService) : ViewModel() {

    companion object {
        fun factory(service: GamesService) = viewModelFactory {
            initializer { GameViewModel(service) }
        }
    }

    private val errorFlow = MutableStateFlow<LoadState<String>>(LoadState.Idle)

    private val createGameFlow: MutableStateFlow<LoadState<String>> = MutableStateFlow(LoadState.Idle)

    private val gameFlow: MutableStateFlow<LoadState<Game>> = MutableStateFlow(LoadState.Idle)

    private val gameIdFlow: MutableStateFlow<LoadState<String>> = MutableStateFlow(LoadState.Idle)

    val error: Flow<LoadState<String>>
        get() = errorFlow.asStateFlow()

    val createGame: Flow<LoadState<String>>
        get() = createGameFlow.asStateFlow()

    val game: Flow<LoadState<Game>>
        get() = gameFlow.asStateFlow()

    val gameId: Flow<LoadState<String>>
        get() = gameIdFlow.asStateFlow()

    fun resetStartGame() {
        createGameFlow.value = createGameFlow.value.resetToIdle()
    }

    fun errorToIdle() {
        errorFlow.value = errorFlow.value.resetToIdle()
    }
}

