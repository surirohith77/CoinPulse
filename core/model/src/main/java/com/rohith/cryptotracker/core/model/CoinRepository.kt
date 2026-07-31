package com.rohith.cryptotracker.core.model

import kotlinx.coroutines.flow.Flow

/**
 * Interface contract defining domain repository operations for cryptocurrency data.
 */
interface CoinRepository {
    
    fun getCoins(): Flow<List<Coin>>
    
    suspend fun refreshCoins(): Result<Unit>
    
    suspend fun getCoinDetail(id: String): Result<CoinDetail>
    
    suspend fun getMarketChart(id: String, days: Int): Result<List<HistoricalPrice>>
}
