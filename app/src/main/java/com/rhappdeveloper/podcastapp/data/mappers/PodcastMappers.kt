package com.rhappdeveloper.podcastapp.data.mappers

import com.rhappdeveloper.podcastapp.data.database.PodcastEntity
import com.rhappdeveloper.podcastapp.data.remote.dto.Podcast

fun Podcast.toPodcastEntity(): PodcastEntity {
    return PodcastEntity(
        id = 0,
        apiId = id,
        title = title,
        publisherName = publisher,
        icon = image,
        isFavourite = false
    )
}

fun PodcastEntity.toPodcasts(): com.rhappdeveloper.podcastapp.domain.Podcast {
    return com.rhappdeveloper.podcastapp.domain.Podcast(
        id = id,
        apiId = apiId,
        title = title,
        publisherName = publisherName,
        icon = icon,
        isFavourite = isFavourite
    )
}