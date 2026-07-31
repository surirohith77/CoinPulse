package com.rohith.cryptotracker.data

import com.google.common.truth.Truth.assertThat
import com.rohith.cryptotracker.core.database.dao.CoinDao
import com.rohith.cryptotracker.core.database.model.CoinEntity
import com.rohith.cryptotracker.core.model.Coin
import com.rohith.cryptotracker.core.network.datasource.NetworkDataSource
import com.rohith.cryptotracker.core.network.model.CoinDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/**
 * Unit tests validating offline-first caching mechanics in [CoinRepositoryImpl].
 */
class CoinRepositoryImplTest {

    private lateinit var repository: CoinRepositoryImpl
    private lateinit var networkSource: NetworkDataSource
    private lateinit var coinDao: CoinDao

    @Before
    fun setUp() {
        networkSource = mockk()
        coinDao = mockk()
        repository = CoinRepositoryImpl(networkSource, coinDao)
    }

    @Test
    fun `getCoins returns mapped data from database DAO flow`() = runTest {
        // Arrange
        val dbEntities = listOf(
            CoinEntity(
                id = "bitcoin", symbol = "btc", name = "Bitcoin", image = "",
                currentPrice = 60000.0, marketCap = 1.0, marketCapRank = 1,
                priceChangePercent24h = 2.0, lastUpdated = ""
            )
        )
        every { coinDao.getCoins() } returns flowOf(dbEntities)

        // Act
        val result = repository.getCoins().first()

        // Assert
        assertThat(result).hasSize(1)
        assertThat(result[0].id).isEqualTo("bitcoin")
        assertThat(result[0].symbol).isEqualTo("BTC") // Mapped uppercase symbol
    }

    @Test
    fun `refreshCoins fetches remote API and updates database cache`() = runTest {
        // Arrange
        val remoteDtos = listOf(
            CoinDto(
                id = "bitcoin", symbol = "btc", name = "Bitcoin", image = "",
                currentPrice = 60000.0, marketCap = 1.0, marketCapRank = 1,
                priceChangePercent24h = 2.0, lastUpdated = ""
            )
        )
        coEvery { networkSource.getCoins() } returns Result.success(remoteDtos)
        coEvery { coinDao.refreshCoinsCache(any()) } returns Unit

        // Act
        val result = repository.refreshCoins()

        // Assert
        assertThat(result.isSuccess).isTrue()
        coVerify(exactly = 1) { coinDao.refreshCoinsCache(any()) }
    }

    @Test
    fun `refreshCoins network error returns failure result`() = runTest {
        // Arrange
        val networkError = Exception("Rate limit hit")
        coEvery { networkSource.getCoins() } returns Result.failure(networkError)

        // Act
        val result = repository.refreshCoins()

        // Assert
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Rate limit hit")
        coVerify(exactly = 0) { coinDao.refreshCoinsCache(any()) }
    }
}
