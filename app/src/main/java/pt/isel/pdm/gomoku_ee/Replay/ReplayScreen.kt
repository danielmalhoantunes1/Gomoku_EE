package pt.isel.pdm.gomoku_ee.Replay

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pt.isel.pdm.gomoku_ee.Domain.FavouriteGame
import pt.isel.pdm.gomoku_ee.Domain.Board
import pt.isel.pdm.gomoku_ee.Domain.CellState
import pt.isel.pdm.gomoku_ee.Game.DrawLines
import pt.isel.pdm.gomoku_ee.Game.DrawStone
import pt.isel.pdm.gomoku_ee.Domain.Game
import pt.isel.pdm.gomoku_ee.Main.MakeButton
import pt.isel.pdm.gomoku_ee.R

@Composable
fun ReplayScreen(
    favouriteInfo: FavouriteGame,
    idx: Int,
    game: Game,
    lastpieceplaced: Boolean,
    onMainRequested: () -> Unit,
    onNext: () -> Unit,
    onPrev: () -> Unit,
    onLast: () -> Unit
) {
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
        ReplayBoard(board = game.board)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MakePrevButton(text = "Prev", idx = idx, lastpieceplaced = lastpieceplaced, onLast = { onLast() }) { onPrev() }
            MakeNextButton(text = "Next", lastpieceplaced = lastpieceplaced) { onNext() }
        }
    }
}

@Composable
fun ReplayBoard(board: Board) {
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
                        ReplayCell(
                            stone = board.get(row, col),
                            cellSize = cellSize,
                            boardSize = boardSize,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReplayCell(stone: CellState, cellSize: Dp, boardSize: Int) {
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
                .size(boxSize),
            contentAlignment = Alignment.Center
        ) {
            if (stone != CellState.EMPTY) {
                DrawStone(stone)
            }
        }
    }
}

@Composable
fun MakePrevButton(text: String, size: Dp = 200.dp, idx: Int, lastpieceplaced: Boolean, onLast: () -> Unit, onPrev: () -> Unit) {
    if (lastpieceplaced) Button(
        modifier = Modifier
            .width(size),
        onClick = onLast,
        enabled = idx > 0
    ) {
        Text(text = text)
    }
    else Button(
        modifier = Modifier
            .width(size),
        onClick = onPrev,
        enabled = idx > 0
    ) {
        Text(text = text)
    }
}

@Composable
fun MakeNextButton(text: String, size: Dp = 200.dp, lastpieceplaced: Boolean, onClick: () -> Unit) {
    if (lastpieceplaced) Button(
        modifier = Modifier
            .width(size),
        onClick = onClick,
        enabled = false
    ) {
        Text(text = text)
    }
    else Button(
        modifier = Modifier
            .width(size),
        onClick = onClick,
        enabled = true
    ) {
        Text(text = text)
    }
}