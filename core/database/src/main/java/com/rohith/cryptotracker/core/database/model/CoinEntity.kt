package com.rohith.cryptotracker.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity representing local database cached coin summary data.
 */
@Entity(tableName = "coins")
data class CoinEntity(
    @PrimaryKey val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val currentPrice: Double,
    val marketCap: Double,
    val marketCapRank: Int,
    val priceChangePercent24h: Double,
    val lastUpdated: String
)
