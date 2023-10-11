package com.atg.games

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.atg.base.ConsumableEffect
import com.atg.base.ConsumeEffect
import com.atg.base.SideEffect
import com.atg.common.AddStateAction
import com.atg.common.BindFeature
import com.atg.common.RemoveStateAction
import com.atg.common.State
import com.atg.common.UnbindFeature
import com.atg.common.app.AppState
import com.atg.common.app.AppStore
import com.atg.common.flow.sideEffects
import com.atg.common.flow.typedState
import com.atg.games.redux.GamesAction
import com.atg.games.redux.GamesCommandProps
import com.atg.games.redux.GamesProps
import com.atg.games.redux.GamesState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class GamesViewModel(private val store: AppStore) : ViewModel() {

    val gamesProps = mutableStateOf(GamesProps())
    val gamesCommandProps = mutableStateOf(GamesCommandProps())

    init {
        store.dispatch(AddStateAction(GamesState()), BindFeature(GamesAction::class))
        store.dispatch(GamesAction.LoadAction)
        store.hotState().bind<GamesState, GamesProps>(gamesProps) { it.map() }
        store.sideEffect { gamesCommandProps.value = it.mapCommandProps() }

    }

    private fun GamesState.map() = GamesProps(
        games = games,
        openGameCommand = ::openGame
    )

    private fun ConsumableEffect<GamesAction.OpenGameCommand>.mapCommandProps() =
        GamesCommandProps(sideEffect = this)
    private fun openGame(id: Int) {
        store.dispatch(GamesAction.OpenGameAction(id))
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


    inline fun <reified T : SideEffect> AppStore.sideEffect(crossinline each: (ConsumableEffect<T>) -> Unit) =
        hotState().sideEffects<T>()
            .map { it.firstOrNull() }
            .map { ConsumableEffect(it) { it?.let { dispatch(ConsumeEffect(it)) } } }
            .onEach { each(it) }
            .launchIn(viewModelScope)
    fun search(text: String) {
        println("text = $text")
    }

}