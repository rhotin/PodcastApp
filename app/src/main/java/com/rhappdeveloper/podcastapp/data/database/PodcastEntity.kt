package com.rhappdeveloper.podcastapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PodcastEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val apiId: String,
    val title: String,
    val publisherName: String,
    val icon: String,
    val isFavourite: Boolean = false
)