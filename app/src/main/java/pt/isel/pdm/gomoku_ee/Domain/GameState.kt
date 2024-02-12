package pt.isel.pdm.gomoku_ee.Domain

enum class GameState {
    STARTING,
    ON_GOING,
    FINISHED;

    override fun toString(): String {
        return when {
            this == STARTING -> "Starting"
            this == ON_GOING -> "On Going"
            this == FINISHED -> "Finished"
            else -> throw IllegalArgumentException("Invalid value for GameState")
        }
    }
}