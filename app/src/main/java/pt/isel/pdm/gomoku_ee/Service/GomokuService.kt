package pt.isel.pdm.gomoku_ee.Service

import pt.isel.pdm.gomoku_ee.Domain.FavouriteGame

interface GomokuService {
    suspend fun updateFavInfo(game: FavouriteGame): FavouriteGame

    suspend fun getFavourites() : List<FavouriteGame>
}