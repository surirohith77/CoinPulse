package com.rohith.cryptotracker.core.database.mapper

import com.rohith.cryptotracker.core.model.Coin
import com.rohith.cryptotracker.core.database.model.CoinEntity

fun CoinEntity.toCoin(): Coin {
    return Coin(
        id = id,
        symbol = symbol,
        name = name,
        image = image,
        currentPrice = currentPrice,
        marketCap = marketCap,
        marketCapRank = marketCapRank,
        priceChangePercent24h = priceChangePercent24h,
        lastUpdated = lastUpdated
    )
}

fun Coin.toCoinEntity(): CoinEntity {
    return CoinEntity(
        id = id,
        symbol = symbol,
        name = name,
        image = image,
        currentPrice = currentPrice,
        marketCap = marketCap,
        marketCapRank = marketCapRank,
        priceChangePercent24h = priceChangePercent24h,
        lastUpdated = lastUpdated
    )
}
