package com.example.moviesgrab.networking

import com.example.moviesgrab.MoviesRepository
import com.example.moviesgrab.networking.person.PopularPersons
import com.example.moviesgrab.ui.networkrequestactivity.fragments.searching.dataMovies.ResultMoviesList
import com.example.moviesgrab.ui.networkrequestactivity.fragments.searching.dataTVSeries.ResultTVSeriesList
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchSearchMoviesOrTvSeries @Inject constructor(private val tmdbApi: TMDBApi, val moviesRepository: MoviesRepository) {

    sealed class Result {
        data class Success(val items: ResultMoviesList) : Result()
        data class SuccessTVSeries(val items: ResultTVSeriesList): Result()
        object Failure: Result()
    }


    suspend fun fetch_searched_moives(name1: String, page: Int): Result {
        return withContext(Dispatchers.IO) {
            try {
               // val response = tmdbApi.fetch_searched_movies(name1)
                val response = moviesRepository.getSearchedMovies(name1, page)
                if (response.isSuccessful && response.body() != null) {
                    return@withContext FetchSearchMoviesOrTvSeries.Result.Success(response.body()!!)
                } else {
                    return@withContext FetchSearchMoviesOrTvSeries.Result.Failure
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext FetchSearchMoviesOrTvSeries.Result.Failure
                } else {
                    throw t
                }
            }
        }
    }




    suspend fun fetch_searched_tvseries(name1: String, page: Int): Result {
        return withContext(Dispatchers.IO) {
            try {
                // val response = tmdbApi.fetch_searched_movies(name1)
                val response = moviesRepository.getSearchedTVSeries(name1, page)
                if (response.isSuccessful && response.body() != null) {
                    return@withContext FetchSearchMoviesOrTvSeries.Result.SuccessTVSeries(response.body()!!)
                } else {
                    return@withContext FetchSearchMoviesOrTvSeries.Result.Failure
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext FetchSearchMoviesOrTvSeries.Result.Failure
                } else {
                    throw t
                }
            }
        }
    }






}