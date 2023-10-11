package com.atg.games

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.atg.games.model.ShortGame
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.viewmodel.viewModel
import org.koin.compose.getKoin
import org.koin.mp.KoinPlatform

@Composable
fun GamesList(viewModel: GamesViewModel) {
    Column(
        Modifier.fillMaxWidth().background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val search = remember { mutableStateOf(TextFieldValue("")) }

        OutlinedTextField(
            value = search.value,
            onValueChange = { text -> search.value = text },
            modifier = Modifier.fillMaxWidth().height(64.dp)
        )

        val props = viewModel.gamesProps
        LazyColumn(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            items((props.value.games), key = { it.id }) {
                GameCard(it) { game -> props.value.openGameCommand.invoke(game.id) }
            }
        }
        LaunchedEffect(Unit) {
            snapshotFlow { search.value }
                .debounce(1500)
                .distinctUntilChanged()
                .onEach { viewModel.search(it.text) }
                .launchIn(this)
        }
    }
}

@Composable
private fun GameCard(game: ShortGame, click: (ShortGame) -> Unit) {
    Column(modifier = Modifier.clickable { click.invoke(game) }) {
        Text(text = game.title, modifier = Modifier)
        KamelImage(
            modifier = Modifier.fillMaxSize(100f),
            resource = asyncPainterResource(data = game.thumbnail),
            contentDescription = game.title,
        )

    }
}