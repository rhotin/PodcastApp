package com.rhappdeveloper.podcastapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.rhappdeveloper.podcastapp.ui.theme.PodcastAppTheme
import com.rhappdeveloper.podcastapp.view.PodcastDetailScreen
import com.rhappdeveloper.podcastapp.view.PodcastScreen
import com.rhappdeveloper.podcastapp.view.PodcastViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PodcastAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = hiltViewModel<PodcastViewModel>()
                    val podcasts = viewModel.podcastPagingFlow.collectAsLazyPagingItems()
                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = PodcastScreenRoute
                    ) {
                        composable<PodcastScreenRoute> {
                            PodcastScreen(podcasts, navigationController)
                        }
                        composable<PodcastDetailScreenRoute> {
                            val args = it.toRoute<PodcastDetailScreenRoute>()
                            PodcastDetailScreen(args.id)
                        }
                    }
                }
            }
        }
    }
}

@Serializable
object PodcastScreenRoute

@Serializable
data class PodcastDetailScreenRoute(
    val id: Int
)
