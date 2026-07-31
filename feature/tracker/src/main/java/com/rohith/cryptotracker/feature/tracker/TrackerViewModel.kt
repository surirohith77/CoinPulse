package com.rohith.cryptotracker.feature.tracker

import androidx.lifecycle.ViewModel
import com.rohith.cryptotracker.core.model.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

/**
 * ViewModel for the cryptocurrency dashboard managing states and intents via Orbit MVI.
 */
@HiltViewModel
class TrackerViewModel @Inject constructor(
    private val repository: CoinRepository
) : ViewModel(), ContainerHost<TrackerState, TrackerSideEffect> {

    override val container: Container<TrackerState, TrackerSideEffect> =
        container(TrackerState()) {
            loadCoins()
            refreshCoins()
        }

    private fun loadCoins() = intent {
        reduce { state.copy(isLoading = true) }
        repository.getCoins().collect { coinList ->
            reduce {
                state.copy(
                    coins = coinList,
                    isLoading = false
                )
            }
        }
    }

    fun refreshCoins() = intent {
        reduce { state.copy(isRefreshing = true) }
        val result = repository.refreshCoins()
        reduce { state.copy(isRefreshing = false) }
        result.onFailure { error ->
            postSideEffect(TrackerSideEffect.ShowError(error.localizedMessage ?: "Network synch failed"))
        }
    }

    fun onSearchQueryChange(query: String) = intent {
        reduce { state.copy(searchQuery = query) }
    }

    fun onCoinClicked(coinId: String) = intent {
        postSideEffect(TrackerSideEffect.NavigateToDetail(coinId))
    }
}
