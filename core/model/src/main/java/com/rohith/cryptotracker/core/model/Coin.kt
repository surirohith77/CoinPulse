package com.rohith.cryptotracker.core.model

/**
 * Representation of a cryptocurrency summary for list and dashboard view.
 */
data class Coin(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val currentPrice: Double,
    val marketCap: Double,
    val marketCapRank: Int,
    val priceChangePercent24h: Double,
    val lastUpdated: String
)
