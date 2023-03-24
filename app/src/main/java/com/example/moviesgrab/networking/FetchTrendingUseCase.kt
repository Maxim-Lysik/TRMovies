package com.example.moviesgrab.networking

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchTrendingUseCase @Inject constructor(private val tmdbApi: TMDBApi) {

    sealed class Result {
        data class Success(val items: ResultItems) : Result()
        object Failure: Result()
    }


    suspend fun fetch_treding_movies(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = tmdbApi.fetch_trending_items_movie()
                if (response.isSuccessful && response.body() != null) {
                    return@withContext Result.Success(response.body()!!)
                } else {
                    return@withContext Result.Failure
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext Result.Failure
                } else {
                    throw t
                }
            }
        }
    }


    suspend fun fetch_treding_tv(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = tmdbApi.fetch_trending_items_tv()
                if (response.isSuccessful && response.body() != null) {
                    return@withContext Result.Success(response.body()!!)
                } else {
                    return@withContext Result.Failure
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext Result.Failure
                } else {
                    throw t
                }
            }
        }
    }


}