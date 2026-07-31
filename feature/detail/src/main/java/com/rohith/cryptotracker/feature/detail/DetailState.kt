package com.rohith.cryptotracker.feature.detail

import com.rohith.cryptotracker.core.model.CoinDetail
import com.rohith.cryptotracker.core.model.HistoricalPrice

/**
 * UI State for the cryptocurrency detail metrics screen.
 */
data class DetailState(
    val coinId: String = "",
    val coinDetail: CoinDetail? = null,
    val historicalPrices: List<HistoricalPrice> = emptyList(),
    val chartDays: Int = 7,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    /**
     * Extracts list of raw price coordinates for rendering on custom LineChart Canvas.
     */
    val priceValues: List<Double>
        get() = historicalPrices.map { it.price }
}
