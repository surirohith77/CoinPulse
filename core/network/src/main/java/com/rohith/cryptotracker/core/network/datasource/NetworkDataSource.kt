package com.rohith.cryptotracker.core.network.datasource

import android.util.Log
import com.rohith.cryptotracker.core.network.api.CoinGeckoApi
import com.rohith.cryptotracker.core.network.model.CoinDetailDto
import com.rohith.cryptotracker.core.network.model.CoinDto
import com.rohith.cryptotracker.core.network.model.MarketChartDto
import javax.inject.Inject

interface NetworkDataSource {
    suspend fun getCoins(vsCurrency: String = "usd"): Result<List<CoinDto>>
    suspend fun getCoinDetail(id: String): Result<CoinDetailDto>
    suspend fun getMarketChart(id: String, days: Int = 7): Result<MarketChartDto>
}

/**
 * Implementation of [NetworkDataSource] wrapping [CoinGeckoApi].
 * Automatically switches to local assets/mock fallbacks if the remote service
 * experiences rate-limiting (HTTP 429) or other request-blocking errors.
 */
class NetworkDataSourceImpl @Inject constructor(
    private val api: CoinGeckoApi
) : NetworkDataSource {

    private val tag = "NetworkDataSource"

    override suspend fun getCoins(vsCurrency: String): Result<List<CoinDto>> {
        return try {
            val response = api.getCoins(vsCurrency = vsCurrency)
            Result.success(response)
        } catch (e: Exception) {
            Log.e(tag, "Remote coin fetch failed, serving fallback mock dataset", e)
            Result.success(MockCryptoData.mockCoins)
        }
    }

    override suspend fun getCoinDetail(id: String): Result<CoinDetailDto> {
        return try {
            val response = api.getCoinDetail(id = id)
            Result.success(response)
        } catch (e: Exception) {
            Log.e(tag, "Remote detail fetch failed for $id, serving fallback details", e)
            Result.success(MockCryptoData.getMockDetail(id))
        }
    }

    override suspend fun getMarketChart(id: String, days: Int): Result<MarketChartDto> {
        return try {
            val response = api.getMarketChart(id = id, days = days)
            Result.success(response)
        } catch (e: Exception) {
            Log.e(tag, "Remote market chart failed for $id, serving fallback series", e)
            Result.success(MockCryptoData.getMockHistoricalPrices(days))
        }
    }
}
