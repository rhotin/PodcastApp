package com.rhappdeveloper.podcastapp.view

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.rhappdeveloper.podcastapp.PodcastDetailScreenRoute
import com.rhappdeveloper.podcastapp.R
import com.rhappdeveloper.podcastapp.domain.Podcast
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun PodcastScreen(podcasts: LazyPagingItems<Podcast>, navigationController: NavHostController?) {
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
                            AsyncImage(
                                model = podcasts[index]?.icon,
                                error = rememberAsyncImagePainter(R.drawable.play_icon),
                                contentDescription = "Podcast Icon",
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                            )
                        },
                        modifier = Modifier.clickable {
                            podcasts[index]?.let {
                                navigationController?.navigate(
                                    PodcastDetailScreenRoute(
                                        id = it.id
                                    )
                                )
                            }
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

@Preview(showSystemUi = true)
@Composable
fun PodcastPreview() {
    PodcastScreen(
        podcasts = MutableStateFlow(
            PagingData.from(
                listOf(
                    Podcast(
                        1,
                        "abc123",
                        "title of book",
                        "publisher",
                        "long description about the book",
                        "",
                        false
                    )
                )
            )
        ).collectAsLazyPagingItems(),
        navigationController = null
    )
}
