package com.rhappdeveloper.podcastapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PodcastDetailScreen(id: Int, viewModel: PodcastDetailViewModel = hiltViewModel()) {

    LaunchedEffect(id) {
        viewModel.getPodcastById(id)
    }
    val podcast by viewModel.item.collectAsState()

    if (podcast != null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Podcast Detail Screen: ${podcast?.title}")
        }
    } else {
        CircularProgressIndicator()
    }
}