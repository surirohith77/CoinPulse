package com.rohith.cryptotracker.feature.tracker

import com.google.common.truth.Truth.assertThat
import com.rohith.cryptotracker.core.model.Coin
import com.rohith.cryptotracker.core.model.CoinRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.orbitmvi.orbit.test.test

/**
 * Unit tests validating Orbit MVI flows and state integrity for [TrackerViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TrackerViewModelTest {

    private lateinit var repository: CoinRepository
    private val testDispatcher = StandardTestDispatcher()

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
    fun `initialization loads coins and triggers sync refresh`() = runTest {
        // Arrange
        val mockCoins = listOf(
            Coin(
                id = "bitcoin",
                symbol = "BTC",
                name = "Bitcoin",
                image = "",
                currentPrice = 60000.0,
                marketCap = 100000.0,
                marketCapRank = 1,
                priceChangePercent24h = 2.0,
                lastUpdated = ""
            )
        )
        every { repository.getCoins() } returns flowOf(mockCoins)
        coEvery { repository.refreshCoins() } returns Result.success(Unit)

        // Act
        val viewModel = TrackerViewModel(repository)
        val testSubject = viewModel.test(TrackerState())

        testSubject.runOnCreate()
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        testSubject.assert(TrackerState()) {
            states(
                { copy(isLoading = true) },
                { copy(isLoading = false, coins = mockCoins) },
                { copy(isRefreshing = true, coins = mockCoins) },
                { copy(isRefreshing = false, coins = mockCoins) }
            )
        }
    }

    @Test
    fun `refresh failure posts ShowError side effect`() = runTest {
        // Arrange
        val mockCoins = emptyList<Coin>()
        every { repository.getCoins() } returns flowOf(mockCoins)
        coEvery { repository.refreshCoins() } returns Result.failure(Exception("Sync Error"))

        // Act
        val viewModel = TrackerViewModel(repository)
        val testSubject = viewModel.test(TrackerState())

        testSubject.runOnCreate()
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        testSubject.assert(TrackerState()) {
            states(
                { copy(isLoading = true) },
                { copy(isLoading = false) },
                { copy(isRefreshing = true) },
                { copy(isRefreshing = false) }
            )
            postedSideEffects(
                TrackerSideEffect.ShowError("Sync Error")
            )
        }
    }
}
