package com.example.moviesgrab.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Provides


@Database(entities = [SavedItem::class], version = 1)
//@TypeConverters(Converters::class)
abstract class SavedArticlesDB : RoomDatabase() {

    abstract fun getSavedItemDao(): SavedItemDao

    companion object {
        @Volatile
        private var instance: SavedArticlesDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }


        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                SavedArticlesDB::class.java,
                "saved_items_db.db"
            ).build()
    }

}