package com.atg.games

import androidx.compose.runtime.mutableStateOf
import com.atg.games.model.ShortGame
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class GamesViewModel(private val repository: GameRepository): ViewModel() {

    val props = mutableStateOf<List<ShortGame>>(emptyList())

    fun loadGames() {
        viewModelScope.launch {
            props.value = repository.games()
        }
    }

    fun search(text: String) {
        println("text = $text")
    }

}