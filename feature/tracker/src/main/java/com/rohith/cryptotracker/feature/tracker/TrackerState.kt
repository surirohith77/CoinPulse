package com.rohith.cryptotracker.feature.tracker

import com.rohith.cryptotracker.core.model.Coin

/**
 * UI State for the cryptocurrency tracker dashboard screen.
 */
data class TrackerState(
    val coins: List<Coin> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false
) {
    /**
     * Filters coins in-memory based on name or symbol search queries.
     */
    val filteredCoins: List<Coin>
        get() = coins.filter {
            it.name.contains(searchQuery, ignoreCase = true) ||
            it.symbol.contains(searchQuery, ignoreCase = true)
        }
}
