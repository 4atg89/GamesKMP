package com.atg.games

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.atg.common.AddStateAction
import com.atg.common.BindFeature
import com.atg.common.RemoveStateAction
import com.atg.common.State
import com.atg.common.UnbindFeature
import com.atg.common.app.AppState
import com.atg.common.app.AppStore
import com.atg.common.flow.typedState
import com.atg.games.model.ShortGame
import com.atg.games.redux.GamesAction
import com.atg.games.redux.GamesProps
import com.atg.games.redux.GamesState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class GamesViewModel(
//    private val repository: GameRepository,
    private val store: AppStore
) : ViewModel() {

    val gamesProps = mutableStateOf(GamesProps())

    init {
        store.dispatch(AddStateAction(GamesState()), BindFeature(GamesAction::class))
        store.dispatch(GamesAction.LoadAction)
        store.hotState().bind<GamesState, GamesProps>(gamesProps) {
            println("hotState -> ${it.games}")
            GamesProps(it.games) }
    }

    override fun onCleared() {
        store.dispatch(
            UnbindFeature(GamesAction::class),
            RemoveStateAction(store.state.states.find { it::class == GamesState::class }!!)
        )
        super.onCleared()
    }

    inline fun <reified T : State, reified R> Flow<AppState>.bind(
        props: MutableState<R>,
        crossinline transform: (T) -> R
    ) = typedState<T>()
            .onEach { props.value = transform(it) }
            .launchIn(viewModelScope)

    fun search(text: String) {
        println("text = $text")
    }

}