package com.rohith.cryptotracker.core.network.mapper

import com.rohith.cryptotracker.core.model.Coin
import com.rohith.cryptotracker.core.model.CoinDetail
import com.rohith.cryptotracker.core.model.HistoricalPrice
import com.rohith.cryptotracker.core.network.model.CoinDetailDto
import com.rohith.cryptotracker.core.network.model.CoinDto
import com.rohith.cryptotracker.core.network.model.MarketChartDto

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        symbol = symbol.uppercase(),
        name = name,
        image = image,
        currentPrice = currentPrice,
        marketCap = marketCap,
        marketCapRank = marketCapRank,
        priceChangePercent24h = priceChangePercent24h,
        lastUpdated = lastUpdated ?: ""
    )
}

fun CoinDetailDto.toCoinDetail(): CoinDetail {
    return CoinDetail(
        id = id,
        name = name,
        symbol = symbol.uppercase(),
        description = description.en ?: "",
        image = image.large ?: "",
        currentPriceUsd = marketData.currentPrice.usd ?: 0.0,
        marketCapUsd = marketData.marketCap.usd ?: 0.0,
        totalVolumeUsd = marketData.totalVolume.usd ?: 0.0,
        high24hUsd = marketData.high24h.usd ?: 0.0,
        low24hUsd = marketData.low24h.usd ?: 0.0,
        priceChangePercent24h = marketData.priceChangePercentage24h,
        athUsd = marketData.ath.usd ?: 0.0,
        atlUsd = marketData.atl.usd ?: 0.0
    )
}

fun MarketChartDto.toHistoricalPrices(): List<HistoricalPrice> {
    return prices.mapNotNull { pricePoint ->
        if (pricePoint.size >= 2) {
            HistoricalPrice(
                timestamp = pricePoint[0].toLong(),
                price = pricePoint[1]
            )
        } else {
            null
        }
    }
}
