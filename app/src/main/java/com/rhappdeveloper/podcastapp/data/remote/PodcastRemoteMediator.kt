package com.rhappdeveloper.podcastapp.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.rhappdeveloper.podcastapp.data.database.PodcastDatabase
import com.rhappdeveloper.podcastapp.data.database.PodcastEntity
import com.rhappdeveloper.podcastapp.data.mappers.toPodcastEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PodcastRemoteMediator(
    private val podcastDatabase: PodcastDatabase,
    private val podcastApi: PodcastApi
) : RemoteMediator<Int, PodcastEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PodcastEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    1
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }
            val podcasts = podcastApi.getPodcasts(
                page = loadKey,
                pageCount = state.config.pageSize,
                genreID = 63,
                region = "us"
            )

            podcastDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    podcastDatabase.dao.clearAll()
                }
                val podcastEntity = podcasts.podcasts.map { it.toPodcastEntity() }
                podcastDatabase.dao.upsertAll(podcastEntity)
            }

            MediatorResult.Success(
                endOfPaginationReached = podcasts.podcasts.isEmpty()
            )

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}