package com.example.moviesgrab.networking

import com.example.moviesgrab.networking.person.PopularPersons
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchPopularPersons @Inject constructor(private val tmdbApi: TMDBApi) {

    sealed class Result {
        data class Success(val items: PopularPersons) : Result()
        object Failure: Result()
    }


    suspend fun fetch_popular_persons(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = tmdbApi.fetch_popular_persons()
                if (response.isSuccessful && response.body() != null) {
                    return@withContext FetchPopularPersons.Result.Success(response.body()!!)
                } else {
                    return@withContext FetchPopularPersons.Result.Failure
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext FetchPopularPersons.Result.Failure
                } else {
                    throw t
                }
            }
        }
    }




}