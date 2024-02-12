package pt.isel.pdm.gomoku_ee.AddFavourite

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import pt.isel.pdm.gomoku_ee.Domain.FavouriteGame
import pt.isel.pdm.gomoku_ee.R
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFavouriteScreen(
    plays: Array<Pair<Int, Char>>,
    addFavourite: (FavouriteGame) -> Unit = {},
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
        var textFieldValue by remember { mutableStateOf(TextFieldValue()) }
        var textFieldValue2 by remember { mutableStateOf(TextFieldValue()) }
        var name by remember { mutableStateOf("") }
        var opp by remember { mutableStateOf("") }
        TextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            label = { Text("Game title") }
        )
        TextField(
            value = textFieldValue2,
            onValueChange = { textFieldValue2 = it },
            label = { Text("Name of Opponent") }
        )
        Button(onClick = {name = textFieldValue.text; opp = textFieldValue2.text
            addFavourite(FavouriteGame(name, opp, LocalDate.now(), plays))})
        {
            Text("Confirm")
        }
    }
}
