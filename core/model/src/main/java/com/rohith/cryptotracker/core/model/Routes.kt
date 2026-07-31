package com.rohith.cryptotracker.core.model

import kotlinx.serialization.Serializable

/**
 * Type-safe navigation route definitions.
 */

@Serializable
object TrackerRoute

@Serializable
data class DetailRoute(val coinId: String)
