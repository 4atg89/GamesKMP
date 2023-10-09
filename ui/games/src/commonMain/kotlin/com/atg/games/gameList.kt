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
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.atg.games.model.ShortGame
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.koin.mp.KoinPlatform


@Composable
fun GamesList(click: (Int) -> Unit) {
    Column(
        Modifier.fillMaxWidth().background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val props = remember { mutableStateListOf<ShortGame>() }

        val search = remember { mutableStateOf(TextFieldValue("")) }

        OutlinedTextField(
            value = search.value,
            onValueChange = { text -> search.value = text },
            modifier = Modifier.fillMaxWidth().height(64.dp)
//                .onFocusChanged { focusState ->
//                    if (focusState.isFocused)
//                        search.value =
//                            search.value.copy(selection = TextRange(0, search.value.text.length))
//                }
        )

        LazyColumn(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f)) {
            items(props, key = { it.id }) {
                Column(modifier = Modifier.clickable { click.invoke(it.id) }) {
                    Text(text = it.title, modifier = Modifier)
                    KamelImage(
                        modifier = Modifier.fillMaxSize(100f),
                        resource = asyncPainterResource(data = it.thumbnail),
                        contentDescription = it.title,
                    )

                }

            }
        }
        val koin = KoinPlatform.getKoin()
        LaunchedEffect(key1 = Unit) {
            props.addAll(koin.get<GameRepository>().games())
        }

    }
}