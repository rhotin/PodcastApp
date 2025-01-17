package com.rhappdeveloper.podcastapp.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.rhappdeveloper.podcastapp.PodcastDetailScreenRoute
import com.rhappdeveloper.podcastapp.R
import com.rhappdeveloper.podcastapp.domain.Podcast

@Composable
fun PodcastScreen(podcasts: LazyPagingItems<Podcast>, navigationController: NavHostController) {
    val context = LocalContext.current
    LaunchedEffect(key1 = podcasts.loadState) {
        if (podcasts.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (podcasts.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        if (podcasts.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(
                    count = podcasts.itemCount,
                    key = podcasts.itemKey(),
                    contentType = podcasts.itemContentType()
                ) { index ->
                    ListItem(
                        overlineContent = {
                            podcasts[index]?.let {
                                Text(
                                    text = it.title,
                                    fontSize = 20.sp
                                )
                            }
                        },
                        headlineContent = {
                            podcasts[index]?.let {
                                Text(
                                    text = it.publisherName,
                                    fontSize = 15.sp
                                )
                            }
                        },
                        supportingContent = {
                            podcasts[index]?.let {
                                Text(
                                    text = if (it.isFavourite) stringResource(R.string.favourited)
                                    else "",
                                    color = Color.Red
                                )
                            }
                        },
                        leadingContent = {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(context)
                                        .data(podcasts[index]?.icon)
                                        .error(R.drawable.play_icon)
                                        .build()
                                ),
                                contentDescription = "Remote Image",
                                modifier = Modifier.size(50.dp)
                            )
                        },
                        modifier = Modifier.clickable {
                            podcasts[index]?.let {
                                navigationController.navigate(
                                    PodcastDetailScreenRoute(
                                        id = it.id
                                    )
                                )
                            }
//                            podcasts[index]?.let {
//                                Toast
//                                    .makeText(context, it.title, Toast.LENGTH_SHORT)
//                                    .show()
//                            }
                        }
                    )
                }
                item {
                    if (podcasts.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
