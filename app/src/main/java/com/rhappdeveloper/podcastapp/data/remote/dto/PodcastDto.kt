package com.rhappdeveloper.podcastapp.data.remote.dto


import kotlinx.serialization.SerialName

data class PodcastDto(
    @SerialName("has_next")
    val hasNext: Boolean,
    @SerialName("has_previous")
    val hasPrevious: Boolean,
    @SerialName("id")
    val id: Int,
    @SerialName("listennotes_url")
    val listennotesUrl: String,
    @SerialName("name")
    val name: String,
    @SerialName("next_page_number")
    val nextPageNumber: Int,
    @SerialName("page_number")
    val pageNumber: Int,
    @SerialName("parent_id")
    val parentId: Int,
    @SerialName("podcasts")
    val podcasts: List<Podcast>,
    @SerialName("previous_page_number")
    val previousPageNumber: Int,
    @SerialName("total")
    val total: Int
)