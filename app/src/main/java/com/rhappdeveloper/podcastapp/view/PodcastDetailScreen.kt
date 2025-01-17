package com.rhappdeveloper.podcastapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.rhappdeveloper.podcastapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PodcastDetailScreen(
    id: Int,
    viewModel: PodcastDetailViewModel = hiltViewModel(),
    navController: NavController
) {

    LaunchedEffect(id) {
        viewModel.getPodcastById(id)
    }
    val podcast by viewModel.item.collectAsState()
    val scrollState = rememberScrollState()

    if (podcast != null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Back") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "${podcast?.title}",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "${podcast?.publisherName}")
                AsyncImage(
                    model = podcast?.icon,
                    contentDescription = "Podcast Icon",
                    modifier = Modifier
                        .size(300.dp)
                        .clip(RoundedCornerShape(10.dp)),
                )
                Button(modifier = Modifier
                    .padding(30.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ),
                    onClick = {
                        podcast?.let {
                            viewModel.updatePodcast(it.copy(isFavourite = !it.isFavourite))
                        }
                    }) {
                    Text(
                        text = if (podcast?.isFavourite == true) {
                            stringResource(R.string.favourited)
                        } else {
                            stringResource(R.string.favourite)
                        }
                    )
                }
                Text(
                    text = "${podcast?.description}",
                    modifier = Modifier.padding(30.dp, 0.dp, 30.dp, 30.dp)
                )
            }
        }
    } else {
        CircularProgressIndicator()
    }
}