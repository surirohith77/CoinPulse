package com.rohith.cryptotracker.core.network.model

import com.google.gson.annotations.SerializedName

/**
 * API data transfer object representing detailed coin metrics.
 */
data class CoinDetailDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("description") val description: DescriptionDto,
    @SerializedName("image") val image: ImageDto,
    @SerializedName("market_data") val marketData: MarketDataDto
)

data class DescriptionDto(
    @SerializedName("en") val en: String?
)

data class ImageDto(
    @SerializedName("large") val large: String?
)

data class MarketDataDto(
    @SerializedName("current_price") val currentPrice: CurrencyValueDto,
    @SerializedName("market_cap") val marketCap: CurrencyValueDto,
    @SerializedName("total_volume") val totalVolume: CurrencyValueDto,
    @SerializedName("high_24h") val high24h: CurrencyValueDto,
    @SerializedName("low_24h") val low24h: CurrencyValueDto,
    @SerializedName("price_change_percentage_24h") val priceChangePercentage24h: Double,
    @SerializedName("ath") val ath: CurrencyValueDto,
    @SerializedName("atl") val atl: CurrencyValueDto
)

data class CurrencyValueDto(
    @SerializedName("usd") val usd: Double?
)
