package pt.isel.pdm.gomoku_ee.Domain

data class Board(
    private val cells: Array<Array<CellState>>,
    private val size: Int,
    private val turn: Char
) {
    fun get(row: Int, col: Int) = cells[row][col]
    fun mutate(currTurn: CellState, nextTurn: Char, row: Int, col: Int): Board {
        val newBoardCells = Array(size) { r -> Array(size) { c -> cells[r][c] } }
        newBoardCells[row][col] = currTurn
        return Board(newBoardCells, size, nextTurn)
    }

    fun unmutate(currTurn: CellState, nextTurn: Char, row: Int, col: Int): Board {
        val newBoardCells = Array(size) { r -> Array(size) { c -> cells[r][c] } }
        newBoardCells[row][col] = CellState.EMPTY
        return Board(newBoardCells, size, nextTurn)
    }

    private fun getNumberOfCells (): Int {
        var totalCells = 0
        cells.forEach { it.forEach { cell-> if (cell != CellState.EMPTY) totalCells++ } }
        return totalCells
    }

    fun checkIfWin(row: Int, col: Int): Boolean {
        val adjacentCells = arrayOf(
            Pair(-1, -1),
            Pair(-1, 0),
            Pair(-1, 1),
            Pair(0, -1),
            Pair(0, 1),
            Pair(1, -1),
            Pair(1, 0),
            Pair(1, 1)
        )
        adjacentCells.forEach {
            if (checkCellsInLine(row, col, it.first, it.second, 0, false) == 4) return true
        }
        return false
    }

    fun checkIfDraw() = getNumberOfCells() + 1 == size * size

    private fun checkCellsInLine(row: Int, col: Int, rowInc: Int, colInc: Int, currCount: Int, onRepeat: Boolean): Int {
        var currRow = row + rowInc
        var currCol = col + colInc
        var count = currCount
        while (
            currRow in 0 until size && currCol in 0 until size
            && get(currRow, currCol) == CellState.fromChar(turn)
        ) {
            count++
            currRow += rowInc
            currCol += colInc
        }
        return if (count <= 4 && !onRepeat) checkCellsInLine(row, col, -rowInc, -colInc, count, true)
        else count
    }

    fun getSize() = size
    fun getCurrTurn() = turn

    companion object {
        fun create(size: Int) =
            Board(Array(size) { Array(size) { CellState.EMPTY } }, size, 'B')
    }

    override fun toString(): String =
        cells.flatMap { row -> row.map { it.char } }.joinToString("") + "/$size/$turn"
}
