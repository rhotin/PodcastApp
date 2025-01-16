package com.rhappdeveloper.podcastapp.data.remote

import com.rhappdeveloper.podcastapp.data.remote.dto.PodcastDto
import retrofit2.http.Query
import retrofit2.http.GET

interface PodcastApi {

    @GET("best_podcasts")
    suspend fun getPodcasts(
        @Query("page") page: Int,
        @Query("per_page") pageCount: Int,
        @Query("genre_id") genreID: Int = 68,
        @Query("region") region: String = "us"
    ): PodcastDto

    companion object {
        const val BASE_URL = "https://listen-api-test.listennotes.com/api/v2/"
    }
}