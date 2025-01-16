package com.rhappdeveloper.podcastapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.rhappdeveloper.podcastapp.data.database.PodcastEntity
import com.rhappdeveloper.podcastapp.data.mappers.toPodcasts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class PodcastViewModel @Inject constructor(
    pager: Pager<Int, PodcastEntity>
) : ViewModel() {
    val podcastPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toPodcasts() }
        }
        .cachedIn(viewModelScope)
}