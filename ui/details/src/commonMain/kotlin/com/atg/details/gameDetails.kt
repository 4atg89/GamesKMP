package com.atg.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atg.games.GameRepository
import com.atg.games.model.Game
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject
import org.koin.mp.KoinPlatform
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun GameDetails(id: Int, navigator: Navigator) {
    val game = remember { mutableStateOf<Game?>(null) }
    Column {
        Button(
            modifier = Modifier.size(24.dp),
            onClick = navigator::goBack
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack, contentDescription = ""
            )
        }
        Column {
            Text(text = game.value?.short?.title ?: "", modifier = Modifier)
            KamelImage(
                modifier = Modifier.fillMaxSize(100f),
                resource = asyncPainterResource(data = game.value?.short?.thumbnail ?: ""),
                contentDescription = game.value?.short?.title,
            )

        }
        val repo = koinInject<GameRepository>()
        LaunchedEffect(key1 = Unit) {
            game.value = repo.game(id)
        }
    }
}