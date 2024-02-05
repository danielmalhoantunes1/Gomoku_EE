package pt.isel.pdm.gomoku_ee

data class GamePlayInputModel(val row: Int, val col: Char)

data class GameCreateInputModel(val userId: Int, val rule: String, val boardSize: Int, val token: String)

