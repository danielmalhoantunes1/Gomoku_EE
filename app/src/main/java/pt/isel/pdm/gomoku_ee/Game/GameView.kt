package pt.isel.pdm.gomoku_ee.Game

import pt.isel.pdm.gomoku_ee.R

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.gomoku_ee.Domain.Board
import pt.isel.pdm.gomoku_ee.Domain.CellState
import pt.isel.pdm.gomoku_ee.Domain.Game
import pt.isel.pdm.gomoku_ee.Domain.GameState
import pt.isel.pdm.gomoku_ee.Domain.GamePlayInputModel
import pt.isel.pdm.gomoku_ee.Main.MakeButton

@Composable
fun GameView(game: Game, timer: Long, onMainRequested: () -> Unit = {}, onAddToFavourites: () -> Unit = {}, onUpdate: (GamePlayInputModel) -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background_wood),
                contentScale = ContentScale.FillBounds
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MakeButton(text = "Main Page") { onMainRequested()}
        if (game.isFinished()) {
            MakeButton(text = "Add Game to Favourites") { onAddToFavourites() }
        }else Text(text = "Turn time left: ${30-timer}s", color = Color.White)
        CurrentTurn(game)
        GomokuBoard(game.board) { row, col ->
            val input = GamePlayInputModel(
                row = row + 1,
                col = 'a'.plus(col)
            )
            onUpdate(input)
        }
    }
}

@Composable
fun CurrentTurn(game: Game) {
    if (game.state == GameState.FINISHED && game.winner == 0) {
        Text("Draw!", fontSize = 24.sp, color = Color.White)
    } else if (game.winner == 0) {
        val currentTurn = "Turn: " + if (game.board.getCurrTurn() == 'B') "Black" else "White"
        Text(currentTurn, fontSize = 24.sp, color = Color.White)
    } else {
        val winner = (if (game.winner == game.playerB) "Black" else "White") + " won!"
        Text(winner, fontSize = 24.sp, color = Color.White)
    }
}

@Composable
fun GomokuBoard(board: Board, onCellClicked: (row: Int, col: Int) -> Unit) {
    val boardSize = board.getSize()
    val cellSize = if (boardSize == 15) 25.dp else 20.dp
    Box(
        modifier = Modifier
            .border(2.dp, Color.Black)
            .size(cellSize * boardSize)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            for (row in 0 until boardSize) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    for (col in 0 until boardSize) {
                        Cell(
                            stone = board.get(row, col),
                            cellSize = cellSize,
                            boardSize = boardSize,
                            onClick = { onCellClicked(row, col) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Cell(stone: CellState, cellSize: Dp, boardSize: Int, onClick: () -> Unit) {
    val boxSize = 20.dp
    Box(
        modifier = Modifier
            .size(cellSize)
            .background(Color(android.graphics.Color.parseColor("#EBBD81"))),
        contentAlignment = Alignment.Center
    ) {
        DrawLines(boardSize)
        Box(
            modifier = Modifier
                .size(boxSize)
                .clickable(enabled = stone == CellState.EMPTY) { onClick() },
            contentAlignment = Alignment.Center
        ) {
            if (stone != CellState.EMPTY) {
                DrawStone(stone)
            }
        }
    }
}

@Composable
fun DrawStone(stone: CellState) {
    Image(
        painter = painterResource(
            if (stone == CellState.PLAYER_B) R.drawable.black_stone
            else R.drawable.white_stone
        ),
        contentDescription = if (stone == CellState.PLAYER_B) "Black Stone" else "White Stone",
    )
}

@Composable
fun DrawLines(boardSize: Int) {
    val lineSize = if (boardSize == 15) 36f else 28f
    Canvas(modifier = Modifier) {
        drawLine(
            start = Offset(0f, lineSize),
            end = Offset(0f, -lineSize),
            color = Color.Black,
            strokeWidth = 5F
        )
        drawLine(
            start = Offset(lineSize, 0f),
            end = Offset(-lineSize, 0f),
            color = Color.Black,
            strokeWidth = 5F
        )
    }
}
