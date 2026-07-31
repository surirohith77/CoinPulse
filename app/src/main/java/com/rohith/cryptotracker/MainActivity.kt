package com.rohith.cryptotracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rohith.cryptotracker.core.designsystem.theme.CryptoTrackerTheme
import com.rohith.cryptotracker.core.model.DetailRoute
import com.rohith.cryptotracker.core.model.TrackerRoute
import com.rohith.cryptotracker.feature.detail.DetailScreen
import com.rohith.cryptotracker.feature.detail.DetailViewModel
import com.rohith.cryptotracker.feature.tracker.TrackerScreen
import com.rohith.cryptotracker.feature.tracker.TrackerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = TrackerRoute
                    ) {
                        composable<TrackerRoute> {
                            val viewModel: TrackerViewModel = hiltViewModel()
                            TrackerScreen(
                                viewModel = viewModel,
                                onNavigateToDetail = { coinId ->
                                    navController.navigate(DetailRoute(coinId = coinId))
                                }
                            )
                        }
                        composable<DetailRoute> {
                            val viewModel: DetailViewModel = hiltViewModel()
                            DetailScreen(
                                viewModel = viewModel,
                                onNavigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
