package com.rohith.cryptotracker.data

import com.rohith.cryptotracker.core.database.dao.CoinDao
import com.rohith.cryptotracker.core.database.mapper.toCoin
import com.rohith.cryptotracker.core.database.mapper.toCoinEntity
import com.rohith.cryptotracker.core.model.Coin
import com.rohith.cryptotracker.core.model.CoinDetail
import com.rohith.cryptotracker.core.model.CoinRepository
import com.rohith.cryptotracker.core.model.HistoricalPrice
import com.rohith.cryptotracker.core.network.datasource.NetworkDataSource
import com.rohith.cryptotracker.core.network.mapper.toCoin
import com.rohith.cryptotracker.core.network.mapper.toCoinDetail
import com.rohith.cryptotracker.core.network.mapper.toHistoricalPrices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Single source of truth implementation of [CoinRepository].
 * Mediates between network API requests and local persistence Room database caching.
 */
class CoinRepositoryImpl @Inject constructor(
    private val networkSource: NetworkDataSource,
    private val coinDao: CoinDao
) : CoinRepository {

    override fun getCoins(): Flow<List<Coin>> {
        return coinDao.getCoins().map { entities ->
            entities.map { it.toCoin() }
        }
    }

    override suspend fun refreshCoins(): Result<Unit> {
        return networkSource.getCoins().mapCatching { dtoList ->
            val domainCoins = dtoList.map { it.toCoin() }
            val entities = domainCoins.map { it.toCoinEntity() }
            coinDao.refreshCoinsCache(entities)
        }
    }

    override suspend fun getCoinDetail(id: String): Result<CoinDetail> {
        return networkSource.getCoinDetail(id).mapCatching { dto ->
            dto.toCoinDetail()
        }
    }

    override suspend fun getMarketChart(id: String, days: Int): Result<List<HistoricalPrice>> {
        return networkSource.getMarketChart(id, days).mapCatching { dto ->
            dto.toHistoricalPrices()
        }
    }
}
