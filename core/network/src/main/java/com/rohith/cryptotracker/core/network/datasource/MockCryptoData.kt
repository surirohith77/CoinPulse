package com.rohith.cryptotracker.core.network.datasource

import com.rohith.cryptotracker.core.network.model.CoinDetailDto
import com.rohith.cryptotracker.core.network.model.CoinDto
import com.rohith.cryptotracker.core.network.model.CurrencyValueDto
import com.rohith.cryptotracker.core.network.model.DescriptionDto
import com.rohith.cryptotracker.core.network.model.ImageDto
import com.rohith.cryptotracker.core.network.model.MarketChartDto
import com.rohith.cryptotracker.core.network.model.MarketDataDto
import kotlin.random.Random

/**
 * Fallback local dataset mimicking network payloads to ensure stable application operation
 * under severe public API rate-limits.
 */
object MockCryptoData {

    val mockCoins = listOf(
        CoinDto(
            id = "bitcoin",
            symbol = "btc",
            name = "Bitcoin",
            image = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png",
            currentPrice = 64250.0,
            marketCap = 1260000000000.0,
            marketCapRank = 1,
            priceChangePercent24h = 2.45,
            lastUpdated = "2026-07-30T21:00:00Z"
        ),
        CoinDto(
            id = "ethereum",
            symbol = "eth",
            name = "Ethereum",
            image = "https://assets.coingecko.com/coins/images/279/large/ethereum.png",
            currentPrice = 3450.0,
            marketCap = 414000000000.0,
            marketCapRank = 2,
            priceChangePercent24h = -1.20,
            lastUpdated = "2026-07-30T21:00:00Z"
        ),
        CoinDto(
            id = "solana",
            symbol = "sol",
            name = "Solana",
            image = "https://assets.coingecko.com/coins/images/4128/large/solana.png",
            currentPrice = 182.50,
            marketCap = 84000000000.0,
            marketCapRank = 3,
            priceChangePercent24h = 8.12,
            lastUpdated = "2026-07-30T21:00:00Z"
        ),
        CoinDto(
            id = "cardano",
            symbol = "ada",
            name = "Cardano",
            image = "https://assets.coingecko.com/coins/images/975/large/cardano.png",
            currentPrice = 0.38,
            marketCap = 13500000000.0,
            marketCapRank = 4,
            priceChangePercent24h = -0.45,
            lastUpdated = "2026-07-30T21:00:00Z"
        ),
        CoinDto(
            id = "ripple",
            symbol = "xrp",
            name = "Ripple",
            image = "https://assets.coingecko.com/coins/images/44/large/xrp-symbol-white-128.png",
            currentPrice = 0.59,
            marketCap = 33000000000.0,
            marketCapRank = 5,
            priceChangePercent24h = 1.15,
            lastUpdated = "2026-07-30T21:00:00Z"
        )
    )

    fun getMockDetail(id: String): CoinDetailDto {
        val baseCoin = mockCoins.firstOrNull { it.id == id } ?: mockCoins[0]
        return CoinDetailDto(
            id = baseCoin.id,
            name = baseCoin.name,
            symbol = baseCoin.symbol,
            description = DescriptionDto(
                en = "${baseCoin.name} is a decentralized digital currency, without a central bank or single administrator, that can be sent from user to user on the peer-to-peer network."
            ),
            image = ImageDto(large = baseCoin.image),
            marketData = MarketDataDto(
                currentPrice = CurrencyValueDto(usd = baseCoin.currentPrice),
                marketCap = CurrencyValueDto(usd = baseCoin.marketCap),
                totalVolume = CurrencyValueDto(usd = baseCoin.marketCap * 0.05),
                high24h = CurrencyValueDto(usd = baseCoin.currentPrice * 1.05),
                low24h = CurrencyValueDto(usd = baseCoin.currentPrice * 0.95),
                priceChangePercentage24h = baseCoin.priceChangePercent24h,
                ath = CurrencyValueDto(usd = baseCoin.currentPrice * 1.2),
                atl = CurrencyValueDto(usd = baseCoin.currentPrice * 0.1)
            )
        )
    }

    fun getMockHistoricalPrices(days: Int): MarketChartDto {
        val now = System.currentTimeMillis()
        val interval = 3600000L * 4 // 4 hour steps
        val pointsCount = (days * 24) / 4
        val prices = mutableListOf<List<Double>>()
        
        var currentPrice = 100.0
        for (i in pointsCount downTo 0) {
            val timestamp = now - (i * interval)
            currentPrice += Random.nextDouble(-5.0, 5.5)
            prices.add(listOf(timestamp.toDouble(), currentPrice))
        }
        return MarketChartDto(prices = prices)
    }
}
