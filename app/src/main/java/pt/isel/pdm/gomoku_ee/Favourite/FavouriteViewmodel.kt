package pt.isel.pdm.gomoku_ee.Favourite

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pt.isel.pdm.gomoku_ee.Domain.FavouriteGame
import pt.isel.pdm.gomoku_ee.Domain.LoadState
import pt.isel.pdm.gomoku_ee.Service.GomokuService
import pt.isel.pdm.gomoku_ee.Domain.complete
import pt.isel.pdm.gomoku_ee.Domain.loading

@RequiresApi(Build.VERSION_CODES.O)
class FavouriteViewmodel(private val service: GomokuService): ViewModel() {

    companion object {
        fun factory( service: GomokuService) = viewModelFactory {
            initializer { FavouriteViewmodel(service) }
        }
    }

    private val favouritesFlow: MutableStateFlow<LoadState<List<FavouriteGame>>> = MutableStateFlow(
        LoadState.Idle)

    val favourites: Flow<LoadState<List<FavouriteGame>>>
        get() = favouritesFlow.asStateFlow()

    fun getFavourites(){
        favouritesFlow.value = favouritesFlow.value.loading()
        viewModelScope.launch {
            val result = runCatching { service.getFavourites() }
            favouritesFlow.value = favouritesFlow.value.complete(result)
            Log.v("FFFavs", service.getFavourites().toString())
        }
    }
}
