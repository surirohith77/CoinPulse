package com.rohith.cryptotracker.core.network.model

import com.google.gson.annotations.SerializedName

/**
 * API data transfer object representing basic coin summary.
 */
data class CoinDto(
    @SerializedName("id") val id: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("current_price") val currentPrice: Double,
    @SerializedName("market_cap") val marketCap: Double,
    @SerializedName("market_cap_rank") val marketCapRank: Int,
    @SerializedName("price_change_percentage_24h") val priceChangePercent24h: Double,
    @SerializedName("last_updated") val lastUpdated: String?
)
