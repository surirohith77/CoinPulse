package com.rohith.cryptotracker.feature.tracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
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
 * Orbit MVI VM for tracking dashboard state reductions and side effects.
 */
@HiltViewModel
class TrackerViewModel @Inject constructor(
    private val repository: CoinRepository
) : ViewModel(), ContainerHost<TrackerState, TrackerSideEffect> {

    override val container: Container<TrackerState, TrackerSideEffect> =
        container(TrackerState()) {
            loadData()
        }

    fun loadData() = intent {
        reduce { state.copy(isLoading = true) }
        
        // Fetch background updates from network cache
        val syncResult = repository.refreshCoins()
        syncResult.onFailure { error ->
            postSideEffect(TrackerSideEffect.ShowError(error.localizedMessage ?: "Network sync failed"))
        }

        // Collect database flow for UI updates
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
            postSideEffect(TrackerSideEffect.ShowError(error.localizedMessage ?: "Network sync failed"))
        }
    }

    fun onSearchQueryChange(query: String) = intent {
        reduce { state.copy(searchQuery = query) }
    }

    fun onCoinClicked(coinId: String) = intent {
        postSideEffect(TrackerSideEffect.NavigateToDetail(coinId))
    }
}
