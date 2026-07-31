package com.rohith.cryptotracker.feature.tracker

/**
 * One-time side-effects emitted by the tracker dashboard.
 */
sealed interface TrackerSideEffect {
    data class NavigateToDetail(val coinId: String) : TrackerSideEffect
    data class ShowError(val message: String) : TrackerSideEffect
}
