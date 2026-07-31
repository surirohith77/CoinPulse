package com.rohith.cryptotracker.core.model

/**
 * Detailed metrics and descriptive information for a specific cryptocurrency.
 */
data class CoinDetail(
    val id: String,
    val name: String,
    val symbol: String,
    val description: String,
    val image: String,
    val currentPriceUsd: Double,
    val marketCapUsd: Double,
    val totalVolumeUsd: Double,
    val high24hUsd: Double,
    val low24hUsd: Double,
    val priceChangePercent24h: Double,
    val athUsd: Double,
    val atlUsd: Double
)
