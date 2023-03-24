package com.example.moviesgrab.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface SavedItemDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: SavedItem): Long

    @Query("SELECT * FROM saved_articles")
    fun getAllSavedItems(): LiveData<List<SavedItem>>

   /* @Query("SELECT * FROM saved_articles WHERE link =(:link)")
    fun getByLink(link: String): LiveData<SavedItem>*/

    @Delete
    suspend fun deleteSavedItem(savedItem: SavedItem)


}