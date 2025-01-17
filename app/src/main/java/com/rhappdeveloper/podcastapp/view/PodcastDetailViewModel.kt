package com.rhappdeveloper.podcastapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.rhappdeveloper.podcastapp.data.database.PodcastDatabase
import com.rhappdeveloper.podcastapp.data.database.PodcastEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PodcastDetailViewModel @Inject constructor(
    private val podcastDatabase: PodcastDatabase
) : ViewModel() {

    private val _item = MutableStateFlow<PodcastEntity?>(null)
    val item = _item.asStateFlow()

    fun getPodcastById(id: Int) {
        viewModelScope.launch {
            podcastDatabase.withTransaction {
                _item.value = podcastDatabase.dao.getItemById(id)
            }
        }
    }

    fun updatePodcast(podcast: PodcastEntity) {
        viewModelScope.launch {
            podcastDatabase.withTransaction {
                podcastDatabase.dao.upsertPodcast(podcast)
                _item.value = podcast
            }
        }
    }
}