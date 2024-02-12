package pt.isel.pdm.gomoku_ee.AddFavourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pt.isel.pdm.gomoku_ee.Domain.EmptyOpponent
import pt.isel.pdm.gomoku_ee.Domain.EmptyTitle
import pt.isel.pdm.gomoku_ee.Domain.FavouriteGame
import pt.isel.pdm.gomoku_ee.Domain.LoadState
import pt.isel.pdm.gomoku_ee.Service.GomokuService
import pt.isel.pdm.gomoku_ee.Domain.complete
import pt.isel.pdm.gomoku_ee.Domain.loading

class AddFavouriteViewmodel(private val service: GomokuService) : ViewModel() {

    companion object {
        fun factory(service: GomokuService) = viewModelFactory {
            initializer { AddFavouriteViewmodel(service) }
        }
    }

    private val _favStateFlow : MutableStateFlow<LoadState<FavouriteGame?>> = MutableStateFlow(
        LoadState.Idle)


    val favStateFlow: Flow<LoadState<FavouriteGame?>>
        get() = _favStateFlow.asStateFlow()

    fun addFavouriteGame(game: FavouriteGame){
        if (_favStateFlow.value is LoadState.Idle){
            _favStateFlow.value = _favStateFlow.value.loading()
            viewModelScope.launch {
                val result: Result<FavouriteGame> = when{
                    game.name?.isBlank() == true -> Result.failure(EmptyTitle)
                    game.opponent?.isBlank() == true -> Result.failure(EmptyOpponent)
                    else -> kotlin.runCatching {
                        val favouriteInfo = service.updateFavInfo(game)
                        favouriteInfo
                    }
                }
                _favStateFlow.value = _favStateFlow.value.complete(result)
            }
        }
    }
}
