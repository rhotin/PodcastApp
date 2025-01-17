package com.rhappdeveloper.podcastapp.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PodcastDao {

    @Upsert
    suspend fun upsertAll(podcasts: List<PodcastEntity>)

    @Upsert
    suspend fun upsertPodcast(podcast: PodcastEntity)

    @Query("DELETE FROM podcastentity")
    suspend fun clearAll()

    @Query("SELECT * FROM podcastentity")
    fun pagingSource(): PagingSource<Int, PodcastEntity>

    @Query("SELECT * FROM podcastentity WHERE id = :itemId")
    fun getItemById(itemId: Int): PodcastEntity?
}