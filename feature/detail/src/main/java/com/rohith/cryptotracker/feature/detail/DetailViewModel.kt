package com.rohith.cryptotracker.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.rohith.cryptotracker.core.model.CoinRepository
import com.rohith.cryptotracker.core.model.DetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

/**
 * ViewModel for managing the detailed cryptocurrency metrics, handling dynamic chart ranges.
 */
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: CoinRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ContainerHost<DetailState, DetailSideEffect> {

    private val route = savedStateHandle.toRoute<DetailRoute>()
    private val coinId = route.coinId

    override val container: Container<DetailState, DetailSideEffect> =
        container(DetailState(coinId = coinId)) {
            loadCoinDetail()
            loadHistoricalChart()
        }

    fun loadCoinDetail() = intent {
        reduce { state.copy(isLoading = true) }
        val result = repository.getCoinDetail(state.coinId)
        result.onSuccess { detail ->
            reduce { state.copy(coinDetail = detail, isLoading = false) }
        }.onFailure { error ->
            reduce { state.copy(error = error.localizedMessage, isLoading = false) }
            postSideEffect(DetailSideEffect.ShowToast(error.localizedMessage ?: "Failed loading coin details"))
        }
    }

    fun loadHistoricalChart() = intent {
        val result = repository.getMarketChart(state.coinId, state.chartDays)
        result.onSuccess { prices ->
            reduce { state.copy(historicalPrices = prices) }
        }
    }

    fun onChartDaysChanged(days: Int) = intent {
        reduce { state.copy(chartDays = days) }
        loadHistoricalChart()
    }

    fun onBackClicked() = intent {
        postSideEffect(DetailSideEffect.NavigateBack)
    }
}
