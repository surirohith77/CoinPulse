package com.rohith.cryptotracker.core.model

/**
 * Time-series data point representing historical pricing.
 */
data class HistoricalPrice(
    val timestamp: Long,
    val price: Double
)
