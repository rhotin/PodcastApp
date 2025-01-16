package com.rhappdeveloper.podcastapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PodcastEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PodcastDatabase : RoomDatabase() {
    abstract val dao: PodcastDao
}