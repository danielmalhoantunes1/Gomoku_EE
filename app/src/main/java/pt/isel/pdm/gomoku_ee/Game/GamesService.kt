package pt.isel.pdm.gomoku_ee.Game

import pt.isel.pdm.gomoku_ee.GameCreateInputModel
import pt.isel.pdm.gomoku_ee.GamePlayInputModel

interface GamesService {
    suspend fun create(input: GameCreateInputModel): String
    suspend fun play(input: GamePlayInputModel, userId: Int, gameId: String, token: String): Game
}