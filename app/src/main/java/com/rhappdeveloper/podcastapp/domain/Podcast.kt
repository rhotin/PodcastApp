package com.rhappdeveloper.podcastapp.domain

data class Podcast (
    val id: Int,
    val apiId: String,
    val title: String,
    val publisherName: String,
    val icon: String,
    val isFavourite: Boolean = false
)