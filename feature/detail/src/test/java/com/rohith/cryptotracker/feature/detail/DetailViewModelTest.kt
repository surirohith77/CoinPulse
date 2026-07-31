package com.rohith.cryptotracker.feature.detail

import androidx.lifecycle.SavedStateHandle
import com.rohith.cryptotracker.core.model.CoinDetail
import com.rohith.cryptotracker.core.model.CoinRepository
import com.rohith.cryptotracker.core.model.HistoricalPrice
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.orbitmvi.orbit.test.TestSettings
import org.orbitmvi.orbit.test.test

/**
 * Unit tests validating Orbit MVI flows and state integrity for [DetailViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private lateinit var repository: CoinRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initialization loads details and chart prices`() = runTest(testDispatcher) {
        // Arrange
        val coinId = "bitcoin"
        val savedStateHandle = SavedStateHandle(mapOf("coinId" to coinId))

        val mockDetail = CoinDetail(
            id = coinId,
            name = "Bitcoin",
            symbol = "BTC",
            description = "Bitcoin description",
            image = "",
            currentPriceUsd = 60000.0,
            marketCapUsd = 100000.0,
            totalVolumeUsd = 50000.0,
            high24hUsd = 61000.0,
            low24hUsd = 59000.0,
            priceChangePercent24h = 1.5,
            athUsd = 69000.0,
            atlUsd = 60.0
        )
        val mockChart = listOf(
            HistoricalPrice(1000L, 59500.0),
            HistoricalPrice(2000L, 60000.0)
        )

        coEvery { repository.getCoinDetail(coinId) } returns Result.success(mockDetail)
        coEvery { repository.getMarketChart(coinId, 7) } returns Result.success(mockChart)

        // Act & Assert
        val viewModel = DetailViewModel(repository, savedStateHandle)
        viewModel.test(
            testScope = this,
            initialState = DetailState(coinId = coinId, isLoading = false),
            settings = TestSettings(dispatcherOverride = testDispatcher)
        ) {
            expectState(DetailState(coinId = coinId, isLoading = false))
            
            runOnCreate()
            
            // Assert the final state (intermediate isLoading=true, coinDetail, etc. are conflated)
            expectState { copy(coinDetail = mockDetail, historicalPrices = mockChart, isLoading = false) }
        }
    }

    @Test
    fun `chart days selection triggers historical series fetch`() = runTest(testDispatcher) {
        // Arrange
        val coinId = "bitcoin"
        val savedStateHandle = SavedStateHandle(mapOf("coinId" to coinId))

        val mockDetail = CoinDetail(
            id = coinId, name = "Bitcoin", symbol = "BTC", description = "", image = "",
            currentPriceUsd = 60000.0, marketCapUsd = 1.0, totalVolumeUsd = 1.0,
            high24hUsd = 1.0, low24hUsd = 1.0, priceChangePercent24h = 1.0, athUsd = 1.0, atlUsd = 1.0
        )
        val initialChart = listOf(HistoricalPrice(1000L, 50.0))
        val newChart = listOf(HistoricalPrice(2000L, 75.0))

        coEvery { repository.getCoinDetail(coinId) } returns Result.success(mockDetail)
        coEvery { repository.getMarketChart(coinId, 7) } returns Result.success(initialChart)
        coEvery { repository.getMarketChart(coinId, 30) } returns Result.success(newChart)

        // Act & Assert
        val viewModel = DetailViewModel(repository, savedStateHandle)
        viewModel.test(
            testScope = this,
            initialState = DetailState(coinId = coinId, isLoading = false),
            settings = TestSettings(dispatcherOverride = testDispatcher)
        ) {
            expectState(DetailState(coinId = coinId, isLoading = false))
            
            runOnCreate()
            
            expectState { copy(coinDetail = mockDetail, historicalPrices = initialChart, isLoading = false) }

            // Trigger change
            containerHost.onChartDaysChanged(30)

            // When onChartDaysChanged runs, it reduces chartDays = 30 and then fetches historicalPrices = newChart.
            // Under UnconfinedTestDispatcher, they run synchronously and result in the final state.
            expectState { copy(coinDetail = mockDetail, historicalPrices = newChart, chartDays = 30, isLoading = false) }
        }
    }
}
