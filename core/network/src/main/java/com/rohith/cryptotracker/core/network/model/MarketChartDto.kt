package com.rohith.cryptotracker.core.network.model

import com.google.gson.annotations.SerializedName

/**
 * API data transfer object representing historical price series.
 */
data class MarketChartDto(
    @SerializedName("prices") val prices: List<List<Double>>
)
