package com.example.moviesgrab.networking

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchTrendingById @Inject constructor(private val tmdbApi: TMDBApi) {



    sealed class Result {
        data class Success(val item: TvSeriesSingle) : Result()
        data class SuccessMovie(val item: MovieResultSingle) : Result()
        object Failure: Result()
    }


    suspend fun fetch_treding_tv_by_id(id:String): FetchTrendingById.Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = tmdbApi.fetch_tv_series_by_id(id)
                if (response.isSuccessful && response.body() != null) {
                    return@withContext FetchTrendingById.Result.Success(response.body()!!)
                } else {
                    return@withContext FetchTrendingById.Result.Failure
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext FetchTrendingById.Result.Failure
                } else {
                    throw t
                }
            }
        }
    }



    suspend fun fetch_treding_movie_by_id(id:String): FetchTrendingById.Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = tmdbApi.fetch_movies_by_id(id)
                if (response.isSuccessful && response.body() != null) {
                    return@withContext FetchTrendingById.Result.Success(response.body()!!)
                } else {
                    return@withContext FetchTrendingById.Result.Failure
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext FetchTrendingById.Result.Failure
                } else {
                    throw t
                }
            }
        }
    }



    suspend fun fetch_treding_movie_by_id_for_saving(id:String): FetchTrendingById.Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = tmdbApi.fetch_movies_by_id_for_saving(id)
                if (response.isSuccessful && response.body() != null) {
                    return@withContext FetchTrendingById.Result.SuccessMovie(response.body()!!)
                } else {
                    return@withContext FetchTrendingById.Result.Failure
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext FetchTrendingById.Result.Failure
                } else {
                    throw t
                }
            }
        }
    }








}