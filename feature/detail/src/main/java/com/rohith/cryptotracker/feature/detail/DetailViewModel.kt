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

    private val coinId: String = savedStateHandle["coinId"] ?: ""

    override val container: Container<DetailState, DetailSideEffect> =
        container(DetailState(coinId = coinId)) {
            loadData()
        }

    fun loadData() = intent {
        reduce { state.copy(isLoading = true) }
        
        val detailResult = repository.getCoinDetail(state.coinId)
        detailResult.onSuccess { detail ->
            reduce { state.copy(coinDetail = detail) }
        }.onFailure { error ->
            reduce { state.copy(error = error.localizedMessage) }
            postSideEffect(DetailSideEffect.ShowToast(error.localizedMessage ?: "Failed loading coin details"))
        }

        val chartResult = repository.getMarketChart(state.coinId, state.chartDays)
        chartResult.onSuccess { prices ->
            reduce { state.copy(historicalPrices = prices) }
        }

        reduce { state.copy(isLoading = false) }
    }

    fun onChartDaysChanged(days: Int) = intent {
        reduce { state.copy(chartDays = days) }
        val result = repository.getMarketChart(state.coinId, state.chartDays)
        result.onSuccess { prices ->
            reduce { state.copy(historicalPrices = prices) }
        }
    }

    fun onBackClicked() = intent {
        postSideEffect(DetailSideEffect.NavigateBack)
    }
}
