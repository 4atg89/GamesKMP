package com.atg.games

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import com.atg.games.model.ShortGame
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.koin.mp.KoinPlatform


@Composable
fun GamesList(click: (Int) -> Unit) {
    MaterialTheme {
        var greetingText by remember { mutableStateOf("Hello, World!") }
        Column(
            Modifier.fillMaxWidth().background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val props = remember { mutableStateListOf<ShortGame>() }


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
            Button(onClick = {
                greetingText = "Hello"
            }) {
                Text(greetingText)
            }
            val koin = KoinPlatform.getKoin()
            LaunchedEffect(key1 = Unit) {
                greetingText = "Hello props, "
                props.addAll(koin.get<GameRepository>().games())
            }
        }
    }
}