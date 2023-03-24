package com.example.moviesgrab

import com.example.moviesgrab.db.SavedArticlesDB
import com.example.moviesgrab.db.SavedItem
import com.example.moviesgrab.networking.TMDBApi
import com.example.moviesgrab.ui.networkrequestactivity.fragments.searching.dataMovies.ResultMoviesList
import retrofit2.Response
import javax.inject.Inject

class MoviesRepository@Inject constructor(
    val db : SavedArticlesDB,
    val tmdbApi: TMDBApi
) {

    suspend fun upsert(saved_article: SavedItem) = db.getSavedItemDao().upsert(saved_article)

    fun getSavedItems() = db.getSavedItemDao().getAllSavedItems()

    suspend fun deleteSavedItem(item: SavedItem) = db.getSavedItemDao().deleteSavedItem(item)

    suspend fun getSearchedMovies(search: String, page: Int) = tmdbApi.fetch_searched_movies(search, page)

    suspend fun getSearchedTVSeries(search: String, page: Int) = tmdbApi.fetch_searched_tv_series(search, page)


}