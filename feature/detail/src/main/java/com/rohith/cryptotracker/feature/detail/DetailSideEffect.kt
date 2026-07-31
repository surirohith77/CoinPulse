package com.rohith.cryptotracker.feature.detail

/**
 * One-time side-effects emitted by the detail view.
 */
sealed interface DetailSideEffect {
    data object NavigateBack : DetailSideEffect
    data class ShowToast(val message: String) : DetailSideEffect
}
